package com.example.chan.myanmarcurrencyexchangerate.common.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;

/**
 * Created by CHAN MYAE THU on 1/29/2018.
 */

public final class ConnectionHelper {
    public static final boolean isInternetAvailable() {
        try {
            InetAddress ipAddress = InetAddress.getByName("www.google.com");
            if ("".equals(ipAddress)) {
                return Boolean.FALSE;
            } else {
                return Boolean.TRUE;
            }

        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
}
