package com.example.component_a;

import android.widget.Toast;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.CCUtil;
import com.billy.cc.core.component.IComponent;
import com.example.component_base.ComponentConst;

public class Component_A implements IComponent {
    @Override
    public String getName() {
        //此出的名字是外部调用该组件时传入的名称，用于区分不同的组件
        return ComponentConst.Component_A.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        //此处的action是外部调用该组件内部的方法的标记，用于区分该组件内不同的功能或者方法，
        //由于libraryone依赖了base组件，所以可以直接引用base组件里的常量
        String action = cc.getActionName();
        switch (action) {
            case ComponentConst.Component_A.Action.SHOW:
                ComponentAManager componentAManager = ComponentAManager.getInstance();
                CC.sendCCResult(cc.getCallId(), CCResult.successWithNoKey(componentAManager));
                break;
            case ComponentConst.Component_A.Action.HIDE:
                break;
            case "showComponentA":
                openActivity(cc);
                break;
            case "showComponentB":
                Toast.makeText(cc.getContext(), "showComponentB", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private void openActivity(CC cc) {
        CCUtil.navigateTo(cc, ActivityA.class);
        CC.sendCCResult(cc.getCallId(), CCResult.success());
    }
}
