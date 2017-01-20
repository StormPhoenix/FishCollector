package com.stormphoenix.fishcollector.shared;

import com.stormphoenix.fishcollector.shared.constants.Constants;

/**
 * Created by Developer on 17-1-13.
 * Wang Cheng is a intelligent Android developer.
 */

public class AddressUtils {
    //城市所在省在常量表中的下标
    private static int cityPosition = 0;
    //城市在常量表中的下标
    private static int cityIndex = 0;
    //详细信息
    private static String addressDetails = null;

    //处理地址的函数
    public static void processAddress(String rawAddress) {

        if (rawAddress == null || rawAddress.equals("")) {
            addressDetails = "";
            return;
        }

        int firstIndex = rawAddress.indexOf("|");
        int secIndex = rawAddress.indexOf("$");

        rawAddress.substring(0, firstIndex - 1);

        cityPosition = Integer.parseInt(rawAddress.substring(0, firstIndex));
        cityIndex = Integer.parseInt(rawAddress.substring(firstIndex + 1, secIndex));
        addressDetails = rawAddress.substring(secIndex + 1, rawAddress.length());
    }

    public static int getCityIndex() {
        return cityIndex;
    }

    public static int getCityPosition() {
        return cityPosition;
    }

    public static String getAddressDetails() {
        return addressDetails;
    }

    public static String mergeAddress(String rawAddress) {
        processAddress(rawAddress);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(Constants.PROVINCE[cityPosition])
                .append(Constants.CITY[cityPosition][cityIndex])
                .append(addressDetails);
        return stringBuffer.toString();
    }
}
