package com.example.locationapplication;

import android.app.Application;
import android.content.Context;

public class SDWanVPNApplication extends Application {
    static Context sdWanVPNApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sdWanVPNApplication = this;
    }

    public static Context getAppContext() {
        return sdWanVPNApplication;
    }
}
