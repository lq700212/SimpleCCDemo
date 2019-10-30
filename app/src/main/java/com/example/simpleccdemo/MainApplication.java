package com.example.simpleccdemo;

import android.app.Application;

import com.billy.cc.core.component.CC;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CC.enableVerboseLog(true);
        CC.enableDebug(true);
        CC.enableRemoteCC(true);
    }
}
