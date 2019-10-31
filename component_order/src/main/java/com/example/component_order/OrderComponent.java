package com.example.component_order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.billy.cc.core.component.IComponentCallback;
import com.example.component_base.ComponentConst;
import com.example.component_base.Global;

public class OrderComponent implements IComponent {
    public static final String TAG = "OrderComponent";

    private IComponentCallback printResultCallback = new IComponentCallback() {
        @Override
        public void onResult(CC cc, CCResult result) {
            CCResult ccResult;
            // 根据登录状态决定是否打开页面
            // 这里也可以添加更多的前置判断逻辑
            String username = result.getDataItem(Global.KEY_USERNAME);
            String debugStr = result.getDataItem("debug");
            Log.d(TAG, "username: " + username);//测试组件间的数据通讯是否OK
            Log.d(TAG, "debug: " + debugStr);//两组数据均已测试OK
            if (result.isSuccess()) {
                ccResult = CCResult.success();
                //登录成功，打开目标页面
                startOrderListActivity(cc);
            } else {
                //登录失败，返回失败信息
                ccResult = result;
            }
            //调用方不需要获得额外的信息，只需要知道调用状态
            //所以这个组件采用同步实现：同步调用CC.sendCCResult(...) 并且返回false
            CC.sendCCResult(cc.getCallId(), ccResult);
        }
    };

    @Override
    public String getName() {
        return ComponentConst.Component_order.NAME;
    }

    @Override
    public boolean onCall(CC cc) {
        String action = cc.getActionName();
        switch (action) {
            case ComponentConst.Component_order.Action.OPENORDERACTIVITY:
//                CCResult result = CC.obtainBuilder(ComponentConst.Component_login.NAME)
//                        .setActionName(ComponentConst.Component_login.Action.FORCEGETLOGINUSER)
//                        .build()
//                        .call();
//                CCResult ccResult;
//                // 根据登录状态决定是否打开页面
//                // 这里也可以添加更多的前置判断逻辑
//                String string = result.getDataItem("debug");
//                if (result.isSuccess()) {
//                    ccResult = CCResult.success();
//                    //登录成功，打开目标页面
//                    startOrderListActivity(cc);
//                } else {
//                    //登录失败，返回失败信息
//                    ccResult = result;
//                }
//                //调用方不需要获得额外的信息，只需要知道调用状态
//                //所以这个组件采用同步实现：同步调用CC.sendCCResult(...) 并且返回false
//                CC.sendCCResult(cc.getCallId(), ccResult);
                CC.obtainBuilder(ComponentConst.Component_login.NAME)
                        .setActionName(ComponentConst.Component_login.Action.FORCEGETLOGINUSER)
                        .build()
                        .callAsyncCallbackOnMainThread(printResultCallback);
                break;
        }

        return true;
    }

    private void startOrderListActivity(CC cc) {
        Context context = cc.getContext();
        Intent intent = new Intent(context, OrderListActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
