package info.linlong.didisdk;
//package com.amap.location.demo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.sdu.didi.openapi.DiDiWebActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject; 

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class didiPlugin extends CordovaPlugin {
    private Context context;

    // 高德地图
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;


    private Intent alarmIntent = null;
    private PendingIntent alarmPi = null;
    private AlarmManager alarm = null;

    private static final String APPID = "didi4979445A50797837306D536C537363";
    private static final String SECRET = "66df05b4881de2027d33203eb22afc5d";
    private static final String ALARM_SERVICE = "alarm";
    //private static final String GAODE_APPID = "4f13d132b208041fa38e0ac607178757";



    // 定位信息
    private double latitude = 0L;//纬度
    private double longitude = 0L;//经度
    private String street = "";//街道
    private String locationDetail = "";

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        //AMapLocationClient.setApiKey(GAODE_APPID);
        context = this.cordova.getActivity().getApplicationContext();
        locationClient = new AMapLocationClient(context);
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
         locationClient.setLocationListener(new AMapLocationListener() {
             @Override
             public void onLocationChanged(AMapLocation amapLocation) {
                 if (amapLocation != null) {
                     if (amapLocation.getErrorCode() == 0) {
                         //定位成功回调信息，设置相关消息
                         amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                         latitude = amapLocation.getLatitude();//获取纬度
                         longitude = amapLocation.getLongitude();//获取经度
                         amapLocation.getAccuracy();//获取精度信息
                         locationDetail = amapLocation.getLocationDetail();
                         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                         Date date = new Date(amapLocation.getTime());
                         df.format(date);//定位时间
                         Log.d("MapListener","LocationDetail="+ amapLocation.getLocationDetail());
                     } else {
                         //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                         Log.e("AmapError","location Error, ErrCode:"
                                 + amapLocation.getErrorCode() + ", errInfo:"
                                 + amapLocation.getErrorInfo());
                     }
                 }
             }
         });

        // 创建Intent对象，action为LOCATION
        alarmIntent = new Intent();
        alarmIntent.setAction("LOCATION");

        // 定义一个PendingIntent对象，PendingIntent.getBroadcast包含了sendBroadcast的动作。
        // 也就是发送了action 为"LOCATION"的intent
        alarmPi = PendingIntent.getBroadcast(this.cordova.getActivity(), 0, alarmIntent, 0);
        // AlarmManager对象,注意这里并不是new一个对象，Alarmmanager为系统级服务
        alarm = (AlarmManager) (this.cordova.getActivity().getSystemService(Context.ALARM_SERVICE));

        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        locationClient.startLocation();


    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {

            //HashMap<String, String> map = buildParamMap();
            HashMap<String, String> map = new HashMap<String,String>();
		    //JSONObject param = args.optJSONObject(0);
		    JSONObject jsonObject=new JSONObject();
		    JSONArray jsonArray = jsonObject.getJSONArray(args.toString());
		    JSONObject param = jsonArray.getJSONObject(0);

			Log.d("execute","args="+ args);
			Log.d("execute","param="+ param);
			Log.d("execute","map before="+ map);
			if(null != param){
				String fromlat=String.valueOf(param.opt("fromlat"));
				String fromlng=String.valueOf(param.opt("fromlng"));
				String tolat=String.valueOf(param.opt("tolat"));
				String tolng=String.valueOf(param.opt("tolng"));
				String biz=String.valueOf(param.opt("biz"));
				
				map.put("fromlat", fromlat);//出发地纬度
				map.put("fromlng", fromlng);//出发地经度
				map.put("tolat", tolat);//目的地纬度
				map.put("tolng", tolng);//目的地经度
				map.put("biz", biz);//默认选中的业务线类型。1打车，2专车
			}
	
			Log.d("execute","map after="+ map);
		
            DiDiWebActivity.registerApp(APPID, SECRET);
            DiDiWebActivity.showDDPage(this.cordova.getActivity(), map);
            // js 回调
            callbackContext.success();
            //locationClient.stopLocation();
            //locationClient.onDestroy();
            return true;
        } catch (Exception e) {
            // If we get here, then something horrible has happened
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        }
    }

    private void createAlarm() {
        //设置一个闹钟，2秒之后每隔一段时间执行启动一次定位程序
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 2 * 1000,
                5 * 1000, alarmPi);
    }

    private HashMap<String, String> buildParamMap() {


        locationClient.getLastKnownLocation().getAccuracy();//精确度，准确性
        locationClient.getLastKnownLocation().getAdCode();
        locationClient.getLastKnownLocation().getAddress();
        locationClient.getLastKnownLocation().getAltitude();//高地；高度；
        locationClient.getLastKnownLocation().getAoiName();
        locationClient.getLastKnownLocation().getBearing();
        locationClient.getLastKnownLocation().getCity();
        locationClient.getLastKnownLocation().getCityCode();
        locationClient.getLastKnownLocation().getCountry();
        locationClient.getLastKnownLocation().getDistrict();//区域；地方；行政区
         latitude = locationClient.getLastKnownLocation().getLatitude();//纬度
         longitude = locationClient.getLastKnownLocation().getLongitude();//经度
         street = locationClient.getLastKnownLocation().getStreet();//街道
         locationDetail = locationClient.getLastKnownLocation().getLocationDetail();
        String errorInfo = locationClient.getLastKnownLocation().getErrorInfo();

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("fromlat", String.valueOf(latitude));//出发地纬度
        map.put("fromlng", String.valueOf(longitude));//出发地经度
        //map.put("tolat", String.valueOf(latitude));//目的地纬度
        //map.put("tolng", String.valueOf(longitude));//目的地经度
        map.put("biz", "1");//默认选中的业务线类型。1打车，2专车
        map.put("fromaddr", "");//出发地地址
        map.put("fromname", "");//出发地名称
        map.put("toaddr", "");//目的地地址
        map.put("toname", "");//目的地名称
        map.put("phone", "");//乘客手机号，方便乘客登录使用，会默认补全到登录框中
        map.put("maptype", "soso");
        map.put("errormsg", errorInfo);
        return map;
    }




}
