package com.example.component_a;

import android.util.Log;

import com.example.component_base.UserBean;
import com.example.component_base.interface_custom.IComponentAManager;

public class ComponentAManager implements IComponentAManager {
    private static final String TAG = "ComponentAManager";
    private static ComponentAManager INSTANCE;

    private ComponentAManager() {
    }

    public static ComponentAManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ComponentAManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ComponentAManager();
                }
            }
        }
        return INSTANCE;
    }

    private UserBean getUserBeanFromComponentA() {
        UserBean userBean = new UserBean("ComponentA", 18, 180.7f);
        Log.d(TAG, "getUserBeanFromComponentA: " + userBean.toString());
        return userBean;
    }

    @Override
    public UserBean show() {
        Log.d(TAG, "show: ");
        return getUserBeanFromComponentA();
    }

    @Override
    public void set(UserBean userBean) {

    }
}
