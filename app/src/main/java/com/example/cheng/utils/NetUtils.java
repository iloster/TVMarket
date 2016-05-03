package com.example.cheng.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 16/4/15.
 */
public class NetUtils {

   public static boolean isWifiEnabled(Context context){
       WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
       return wifiManager.isWifiEnabled();
   }

    public static Map<String,String> getWifiInfo(Context context){
        Map map = new HashMap();
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if(isWifiEnabled(context)){
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ip = intToIp(wifiInfo.getIpAddress());
            String ssid = wifiInfo.getSSID();
            map.put("ip",ip);
            map.put("ssid",ssid);
        }
       return map;
    }

    private static String intToIp(int ipAddress)  {
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }
}
