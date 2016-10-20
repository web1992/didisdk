package info.linlong.didisdk;

import android.content.Context;
import android.util.Log;

import com.sdu.didi.openapi.DiDiWebActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;


public class didiPlugin extends CordovaPlugin {
    private Context context;


    private static final String APPID = "didi4979445A50797837306D536C537363";
    private static final String SECRET = "66df05b4881de2027d33203eb22afc5d";


    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {

            HashMap<String, String> map = new HashMap<String, String>();

            Log.d("execute", "args=" + args);
            Log.d("execute", "map before=" + map);
            if (null != args && true) {
                // 这里的经纬度参数是从js 获取的，此版本已经去除了高德定位，可以在之前的发布的0.0.5版本中知道
                // args="[\"39.976694\",\"116.306169\",\"39.976694\",\"116.306169\",\"2\"]";
                JSONArray array = new JSONArray(args.opt(0).toString());
                Log.d("execute", "array=" + array);
                String fromlat = array.optString(0, null);
                String fromlng = array.optString(1, null);
                String tolat = array.optString(2, null);
                String tolng = array.optString(3, null);
                String biz = array.optString(4, null);

                map.put("fromlat", fromlat);//出发地纬度
                map.put("fromlng", fromlng);//出发地经度
                map.put("tolat", tolat);//目的地纬度
                map.put("tolng", tolng);//目的地经度
                map.put("biz", biz);//默认选中的业务线类型。1打车，2专车
                map.put("toaddr", "");//目的地地址
                map.put("toname", "");//目的地名称

            }

            Log.d("execute", "map after=" + map);

            DiDiWebActivity.registerApp(this.cordova.getActivity(), APPID, SECRET);
            DiDiWebActivity.showDDPage(this.cordova.getActivity(), map);
            // js 回调
            callbackContext.success();
            return true;
        } catch (Exception e) {
            // If we get here, then something horrible has happened
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        }
    }


}
