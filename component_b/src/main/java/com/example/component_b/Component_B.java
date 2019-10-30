package com.example.component_b;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.CCUtil;
import com.billy.cc.core.component.IComponent;
import com.example.component_base.ComponentConst;

public class Component_B implements IComponent {
    @Override
    public String getName() {
        //此出的名字是外部调用该组件时传入的名称，用于区分不同的组件
        return ComponentConst.Component_B.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        //此处的action是外部调用该组件内部的方法的标记，用于区分该组件内不同的功能或者方法，
        //由于component b依赖了base组件，所以可以直接引用base组件里的常量
        String action = cc.getActionName();
        switch (action) {
            case ComponentConst.Component_B.Action.SHOW:
                ComponentBManager componentBManager = ComponentBManager.getInstance();
                CC.sendCCResult(cc.getCallId(), CCResult.successWithNoKey(componentBManager));
                break;
            case ComponentConst.Component_B.Action.HIDE:
                break;
            case "showComponentB":
                openActivity(cc);
                break;
        }
        return true;
    }

    private void openActivity(CC cc) {
        CCUtil.navigateTo(cc, ActivityB.class);
        CC.sendCCResult(cc.getCallId(), CCResult.success());
    }
}
