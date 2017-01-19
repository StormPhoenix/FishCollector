package com.stormphoenix.fishcollector.shared;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Developer on 16-12-14.
 * Wang Cheng is a intelligent Android developer.
 */

public class NetManager {
    public static String getBaseUrl() {
        return "http://192.168.0.106:8080/fish/api/";
    }

    public static boolean isNetworkWorkWell(Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();

//    public static boolean checkNetworkType(Context mContext) {
//        try {
//            final ConnectivityManager connectivityManager = (ConnectivityManager) mContext
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            final NetworkInfo mobNetInfoActivity = connectivityManager
//                    .getActiveNetworkInfo();
//            if (mobNetInfoActivity == null || !mobNetInfoActivity.isAvailable()) {
//                return false;
//            } else {
//                // NetworkInfo不为null开始判断是网络类型
//                int netType = mobNetInfoActivity.getType();
//                if (netType == ConnectivityManager.TYPE_WIFI) {
//                    // wifi net处理
//                    return true;
//                } else if (netType == ConnectivityManager.TYPE_MOBILE) {
//                    boolean is3G = isFastMobileNetwork(mContext);
//
//                    final Cursor c = mContext.getContentResolver().query(
//                            PREFERRED_APN_URI, null, null, null, null);
//                    if (c != null) {
//                        c.moveToFirst();
//                        final String user = c.getString(c
//                                .getColumnIndex("user"));
//                        if (!TextUtils.isEmpty(user)) {
//                            if (user.startsWith(CTWAP)) {
//                                return is3G ? TYPE_CT_WAP : TYPE_CT_WAP_2G;
//                            } else if (user.startsWith(CTNET)) {
//                                return is3G ? TYPE_CT_NET : TYPE_CT_NET_2G;
//                            }
//                        }
//                    }
//                    c.close();
//
//                    // 注意三：
//                    // 判断是移动联通wap:
//                    // 其实还有一种方法通过getString(c.getColumnIndex("proxy")获取代理ip
//                    // 来判断接入点，10.0.0.172就是移动联通wap，10.0.0.200就是电信wap，但在
//                    // 实际开发中并不是所有机器都能获取到接入点代理信息，例如魅族M9 （2.2）等...
//                    // 所以采用getExtraInfo获取接入点名字进行判断
//
//                    String netMode = mobNetInfoActivity.getExtraInfo();
//                    Log.i("", "==================netmode:" + netMode);
//                    if (netMode != null) {
//                        // 通过apn名称判断是否是联通和移动wap
//                        netMode = netMode.toLowerCase();
//
//                        if (netMode.equals(CMWAP)) {
//                            return is3G ? TYPE_CM_WAP : TYPE_CM_WAP_2G;
//                        } else if (netMode.equals(CMNET)) {
//                            return is3G ? TYPE_CM_NET : TYPE_CM_NET_2G;
//                        } else if (netMode.equals(NET_3G)
//                                || netMode.equals(UNINET)) {
//                            return is3G ? TYPE_CU_NET : TYPE_CU_NET_2G;
//                        } else if (netMode.equals(WAP_3G)
//                                || netMode.equals(UNIWAP)) {
//                            return is3G ? TYPE_CU_WAP : TYPE_CU_WAP_2G;
//                        }
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return TYPE_OTHER;
//        }
//
//        return TYPE_OTHER;
//    }
//
//    private static boolean isFastMobileNetwork(Context context) {
//        TelephonyManager telephonyManager = (TelephonyManager) context
//                .getSystemService(Context.TELEPHONY_SERVICE);
//
//        switch (telephonyManager.getNetworkType()) {
//            case TelephonyManager.NETWORK_TYPE_1xRTT:
//                return false; // ~ 50-100 kbps
//            case TelephonyManager.NETWORK_TYPE_CDMA:
//                return false; // ~ 14-64 kbps
//            case TelephonyManager.NETWORK_TYPE_EDGE:
//                return false; // ~ 50-100 kbps
//            case TelephonyManager.NETWORK_TYPE_EVDO_0:
//                return true; // ~ 400-1000 kbps
//            case TelephonyManager.NETWORK_TYPE_EVDO_A:
//                return true; // ~ 600-1400 kbps
//            case TelephonyManager.NETWORK_TYPE_GPRS:
//                return false; // ~ 100 kbps
//            case TelephonyManager.NETWORK_TYPE_HSDPA:
//                return true; // ~ 2-14 Mbps
//            case TelephonyManager.NETWORK_TYPE_HSPA:
//                return true; // ~ 700-1700 kbps
//            case TelephonyManager.NETWORK_TYPE_HSUPA:
//                return true; // ~ 1-23 Mbps
//            case TelephonyManager.NETWORK_TYPE_UMTS:
//                return true; // ~ 400-7000 kbps
//            case TelephonyManager.NETWORK_TYPE_EHRPD:
//                return true; // ~ 1-2 Mbps
//            case TelephonyManager.NETWORK_TYPE_EVDO_B:
//                return true; // ~ 5 Mbps
//            case TelephonyManager.NETWORK_TYPE_HSPAP:
//                return true; // ~ 10-20 Mbps
//            case TelephonyManager.NETWORK_TYPE_IDEN:
//                return false; // ~25 kbps
//            case TelephonyManager.NETWORK_TYPE_LTE:
//                return true; // ~ 10+ Mbps
//            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
//                return false;
//            default:
//                return false;
//
//        }
    }
}
