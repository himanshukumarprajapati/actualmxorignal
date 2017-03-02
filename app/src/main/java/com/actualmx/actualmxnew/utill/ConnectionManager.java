package com.actualmx.actualmxnew.utill;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by Office on 28-09-2015.
 */
public class ConnectionManager {

//    public static boolean isNetworkOnlinea(Context context) {
//        boolean status = false;
//        try {
//            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo netInfo = cm.getNetworkInfo(0);
//            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
//                status = true;
//            } else {
//                netInfo = cm.getNetworkInfo(1);
//                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
//                    status = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return status;
//
//    }

       public static boolean isNetworkOnline(Context context) {
        Boolean bNetwork = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        for (NetworkInfo networkInfo : connectivityManager.getAllNetworkInfo()) {
            int netType = networkInfo.getType();
            int netSubType = networkInfo.getSubtype();

            if (netType == ConnectivityManager.TYPE_WIFI) {
                bNetwork = networkInfo.isConnected();
                if (bNetwork == true)
                    break;
            } else if (netType == ConnectivityManager.TYPE_MOBILE && netSubType != TelephonyManager.NETWORK_TYPE_UNKNOWN) {
                bNetwork = networkInfo.isConnected();
                if (bNetwork == true)
                    break;
            } else {
                bNetwork = false;
            }
        }
        if (!bNetwork) {
        }

        return bNetwork;
    }


}
