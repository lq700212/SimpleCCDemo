package com.example.library_two;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.example.component_base.ComponentConst;
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

    @Override
    public UserBean show() {
//        测试代码，在非library_one中无法获取到ComponentAManager实例
//        证实了CC的一个好处，可以很好的隔离个组件的功能界限
//        ComponentAManager componentAManager = ComponentAManager.getInstance();
        return null;
    }

    @Override
    public void set(UserBean userBean) {

    }
}
