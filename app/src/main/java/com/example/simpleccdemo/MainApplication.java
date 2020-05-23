package com.example.simpleccdemo;

import android.app.Application;
import android.content.Context;

import com.billy.cc.core.component.CC;
import com.didi.virtualapk.PluginManager;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CC.enableVerboseLog(true);
        CC.enableDebug(true);
        CC.enableRemoteCC(true);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
    }
}
