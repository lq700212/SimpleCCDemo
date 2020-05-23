package com.example.component_view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.example.component_base.ComponentConst;

public class ViewComponent implements IComponent {
    @Override
    public String getName() {
        return ComponentConst.Component_view.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String action = cc.getActionName();
        BaseActivity activity = null;
        View view = null;
        switch (action) {
            case ComponentConst.Component_view.Action.OPENVIEWACTIVITY:
            case ComponentConst.Component_view.Action.OPENVIEWACTIVITYANDWAITFRAGMENTCREATE:
                openViewActivity(cc);
                return true;
            case ComponentConst.Component_view.Action.CHANGEFRAGMENTTEXT:
                AFragment fragment = cc.getParamItem("Fragment");//接收fragment对象
//                view = cc.getParamItem("View");
                if (fragment != null) {
                    String newString = cc.getParamItem("newString", "修改失败");//接收其它参数
                    fragment.updateTextView(newString);//调用fragment的方法
                    CC.sendCCResult(cc.getCallId(), CCResult.success());//回调结果
                } else {
                    //回调错误信息
                    CC.sendCCResult(cc.getCallId(), CCResult.error("no fragment params"));
                }
                break;
            case ComponentConst.Component_view.Action.CHANGEFRAGMENTCOLOR:
                activity = cc.getParamItem("Activity");
                activity.replaceFramentWithC();
                Intent intent = new Intent(activity, MyService.class);
                activity.startService(intent);
                CC.sendCCResult(cc.getCallId(), CCResult.success());
                break;
        }
        return false;
    }

    private void openViewActivity(CC cc) {
        Context context = cc.getContext();
        Intent intent = new Intent(context, BaseActivity.class);
        //将cc的callId传给Activity，登录完成后通过这个callId来回传结果

        String action = cc.getActionName();
        switch (action) {
            case ComponentConst.Component_view.Action.OPENVIEWACTIVITY:
                intent.putExtra("component_view", cc.getCallId());
                break;
            case ComponentConst.Component_view.Action.OPENVIEWACTIVITYANDWAITFRAGMENTCREATE:
                intent.putExtra("component_view_frag", cc.getCallId());
                break;
        }

        if (!(context instanceof AppCompatActivity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
