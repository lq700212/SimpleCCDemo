package com.example.dynamic_component;

import android.content.Context;
import android.content.Intent;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IDynamicComponent;
import com.example.component_base.ComponentConst;

/**
 * Author: ryan.lei
 * Date: 2020/5/28 17:00
 * Description:
 */
public class DynamicComponent implements IDynamicComponent {
    @Override
    public String getName() {
        return ComponentConst.Dynamic_component.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String action = cc.getActionName();
        Context context = cc.getContext();
        switch (action) {
            case ComponentConst.Dynamic_component.Action.OPEN_ACTIVITY:
                Intent intent = new Intent(context, DynamicComponentActivity.class);
                context.startActivity(intent);
                CC.sendCCResult(cc.getCallId(), CCResult.success());//回调结果
                break;
        }
        return false;
    }
}
