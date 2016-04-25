package com.example.qiezi.view;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mac on 16/1/23.
 */
public class WxtApplication extends Application {
    private static WxtApplication mInstance = null;
    public static String city="温州";
    public boolean k_bKeyRight = true;
    public static int UID;
    public static int ISCAM;
    public static int C_ID=0;
    public static String isFrist="yes";
    public static String TOKEN="null";
    private ExecutorService mExecutorService;
    private final int CORE_POOL_SIZE = 5;
    private ArrayList<WeakReference<OnLowMemoryListener>> mLowMemoryListeners;
    boolean istoken=true;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mInstance = this;
        mLowMemoryListeners = new ArrayList<WeakReference<OnLowMemoryListener>>();
        SharedPreferences sp=getSharedPreferences("UserUID", Context.MODE_PRIVATE);
        UID=sp.getInt("UID", 0);
        isFrist=sp.getString("isFrist", "");
        Log.e("hou", "APPlication:UID=" + UID);
        initMap();
    }

    private void initMap() {
        SDKInitializer.initialize(getApplicationContext());

    }

    public static WxtApplication getInstance() {
        return mInstance;
    }


    public interface OnLowMemoryListener {

        public void onLowMemoryReceived();
    }


    private final ThreadFactory sThreadFactory = new ThreadFactory() {

        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "GreenDroid thread #"
                    + mCount.getAndIncrement());
        }
    };

    public ExecutorService getExecutor() {
        if (mExecutorService == null) {
            mExecutorService = Executors.newFixedThreadPool(CORE_POOL_SIZE,
                    sThreadFactory);
        }
        return mExecutorService;
    }

    public void registerOnLowMemoryListener(OnLowMemoryListener listener) {
        if (listener != null) {
            mLowMemoryListeners.add(new WeakReference<OnLowMemoryListener>(
                    listener));
        }
    }

    /**
     * Remove a previously registered listener
     *
     * @param listener
     *            The listener to unregister
     * @see OnLowMemoryListener
     */
    public void unregisterOnLowMemoryListener(OnLowMemoryListener listener) {
        if (listener != null) {
            int i = 0;
            while (i < mLowMemoryListeners.size()) {
                final OnLowMemoryListener l = mLowMemoryListeners.get(i).get();
                if (l == null || l == listener) {
                    mLowMemoryListeners.remove(i);
                } else {
                    i++;
                }
            }
        }
    }
}
