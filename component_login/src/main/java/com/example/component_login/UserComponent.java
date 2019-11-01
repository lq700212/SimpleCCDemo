package com.example.component_login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.example.component_base.ComponentConst;
import com.example.component_base.Global;

public class UserComponent implements IComponent {
    @Override
    public String getName() {
        return ComponentConst.Component_login.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        Context context;
        Intent intent;
        switch (actionName) {
            case ComponentConst.Component_login.Action.FORCEGETLOGINUSER:
                if (!TextUtils.isEmpty(Global.loginUserName)) {
                    //已登录同步实现，直接调用CC.sendCCResult(...)并返回返回false
                    CCResult result = CCResult.success(Global.KEY_USERNAME, Global.loginUserName).addData("debug", "213413");
                    CC.sendCCResult(cc.getCallId(), result);
                    return false;
                }
                //未登录，打开登录界面，在登录完成后再回调结果，异步实现
                context = cc.getContext();
                intent = new Intent(context, LoginActivity.class);
                if (!(context instanceof Activity)) {
                    //调用方没有设置context或app间组件跳转，context为application
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                //将cc的callId传给Activity，登录完成后通过这个callId来回传结果
                intent.putExtra("callId", cc.getCallId());
                context.startActivity(intent);
                //异步实现，不立即调用CC.sendCCResult,返回true
                return true;
            case ComponentConst.Component_login.Action.OPENLOGINACTIVITY:
                context = cc.getContext();
                intent = new Intent(context, LoginActivity.class);
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
                return true;
        }

        return false;
    }
}
