package info.linlong.didisdk;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
//import com.sdu.didi.openapi.DIOpenSDK;
import com.sdu.didi.openapi.DiDiWebActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

public class didiPlugin extends CordovaPlugin /**implements AMapLocationListener**/ {
    private Context context;

    // 高德地图
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;

    private PendingIntent locationPendingIntent;


    private AlarmManager am;

    //@Override
    //public void onLocationChanged(AMapLocation aMapLocation) {

    //}

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        context = this.cordova.getActivity().getApplicationContext();
        locationClient = new AMapLocationClient(context);
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
       // locationClient.setLocationListener(this);


    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            locationClient.startLocation();
            //locationClient.stopLocation();

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
            double latitude = locationClient.getLastKnownLocation().getLatitude();//纬度
            double longitude = locationClient.getLastKnownLocation().getLongitude();//经度
            String street = locationClient.getLastKnownLocation().getStreet();//街道
            String locationDetail = locationClient.getLastKnownLocation().getLocationDetail();
            String errorInfo = locationClient.getLastKnownLocation().getErrorInfo();

            HashMap<String, String> map = new HashMap<String,String>();

            map.put("fromlat", String.valueOf(latitude));//出发地纬度
            map.put("fromlng", String.valueOf(longitude));//出发地经度
            map.put("tolat", String.valueOf(latitude));//目的地纬度
            map.put("tolng", String.valueOf(longitude));//目的地经度
            map.put("biz", "1");//默认选中的业务线类型。1打车，2专车
            map.put("fromaddr", street);//出发地地址
            map.put("fromname", locationDetail);//出发地名称
            map.put("toaddr", "1");//目的地地址
            map.put("toname", "1");//目的地名称
            map.put("phone", "1");//乘客手机号，方便乘客登录使用，会默认补全到登录框中
            map.put("maptype", "soso");
            map.put("errormsg", errorInfo);
            //APPID: didi4979445A50797837306D536C537363
            //Secret:66df05b4881de2027d33203eb22afc5d
            DiDiWebActivity.registerApp("didi4979445A50797837306D536C537363", "66df05b4881de2027d33203eb22afc5d");
            DiDiWebActivity.showDDPage(this.cordova.getActivity(), map);
           // DIOpenSDK.setMapSdkType(DIOpenSDK.MapLocationType.GAODE);
            return true;
        } catch (Exception e) {
            // If we get here, then something horrible has happened
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        }
    }


}
