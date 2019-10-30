package com.example.component_b;

import android.util.Log;

import com.example.component_base.UserBean;
import com.example.component_base.interface_custom.IComponentBManager;

public class ComponentBManager implements IComponentBManager {
    private static final String TAG = "ComponentBManager";
    private static ComponentBManager INSTANCE;

    private ComponentBManager() {
    }

    public static ComponentBManager getInstance() {
        if (INSTANCE == null) {
            synchronized (ComponentBManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ComponentBManager();
                }
            }
        }
        return INSTANCE;
    }

    private UserBean getUserBeanFromComponentB() {
        UserBean userBean = new UserBean("ComponentB", 23, 146.9f);
        Log.d(TAG, "getUserBeanFromComponentB: " + userBean.toString());
        return userBean;
    }

    @Override
    public UserBean show() {
//        测试代码，在非component_a中无法获取到ComponentAManager实例
//        证实了CC的一个好处，可以很好的隔离个组件的功能界限
//        ComponentAManager componentAManager = ComponentAManager.getInstance();
        Log.d(TAG, "show: ");
        return getUserBeanFromComponentB();
    }

    @Override
    public void set(UserBean userBean) {

    }
}
