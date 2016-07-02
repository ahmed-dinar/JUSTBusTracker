package com.ahmeddinar.justbustracker.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.res.ResourcesCompat;

import com.ahmeddinar.justbustracker.R;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ahmed Dinar on 6/30/2016.
 */
public class MyMapUtils {

    private static Map<String, LatLng> Stoppage;

    public static Map getStoppages(){
        Stoppage = new HashMap<>();
        Stoppage.put("Monihar", new LatLng(23.160685, 89.223263));
        Stoppage.put("Arabpur", new LatLng(23.171482, 89.192601));
        Stoppage.put("Kathaltola", new LatLng(23.1767591, 89.202805));
        Stoppage.put("Chachra", new LatLng(23.144351, 89.196694));
        Stoppage.put("Dharmotala", new LatLng(23.164371, 89.193696));
        Stoppage.put("Palbari", new LatLng(23.180818, 89.191303));
        Stoppage.put("Churamonkathi", new LatLng(23.200445, 89.175988));
        Stoppage.put("JUST", new LatLng(23.233526, 89.124556));

        return Stoppage;
    }


}
