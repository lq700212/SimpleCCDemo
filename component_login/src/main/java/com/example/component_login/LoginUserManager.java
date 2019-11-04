package com.example.component_login;

import android.text.TextUtils;

import com.billy.cc.core.component.CC;
import com.example.component_base.ComponentConst;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LoginUserManager {
    /**
     * 当前登录用户的id
     */
    private static String userId;

    private static final Map<String, String> LOGIN_OBSERVERS = new ConcurrentHashMap<>();

    /**
     * 在登录组件中，登录状态改变时调用此方法更新登录用户信息
     * 同时将最新的用户信息发送给监听者
     */
    public static void refreshLoginUserId(String id) {
        userId = id;
        updateObservers();
    }

    static void addObserver(String componentName, String actionName) {
        if (TextUtils.isEmpty(componentName)) {
            return;
        }
        LOGIN_OBSERVERS.put(componentName, actionName);
        //动态组件在添加监听时立即发送一次当前用户id给它
        notifyObserver(componentName, actionName, userId);
    }

    /**
     * 从监听列表中移除一个动态组件
     */
    static void delObserver(String componentName) {
        if (TextUtils.isEmpty(componentName)) {
            return;
        }
        LOGIN_OBSERVERS.remove(componentName);
    }

    /**
     * 将当前用户id发送给所有正在监听的动态组件
     */
    private static void updateObservers() {
        Set<Map.Entry<String, String>> entries = LOGIN_OBSERVERS.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            notifyObserver(entry.getKey(), entry.getValue(), userId);
        }
    }

    /**
     * 将当前用户id发送给正在监听登录状态的动态组件
     */
    private static void notifyObserver(String componentName, String actionName, String userId) {
        if (TextUtils.isEmpty(componentName)) {
            return;
        }
        CC.obtainBuilder(componentName)
                .setActionName(actionName)
                .addParam(ComponentConst.Component_login.KEY_USER_ID, userId)
                .build().callAsync();
    }
}
