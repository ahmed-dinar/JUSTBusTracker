package com.ahmeddinar.justbustracker.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Ahmed Dinar on 6/29/2016.
 */
public class ConnectionUtils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

}
