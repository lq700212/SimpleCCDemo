package com.example.simpleccdemo;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.example.component_base.ComponentConst;
import com.example.component_base.Global;
import com.example.component_base.UserBean;
import com.example.component_base.interface_custom.IComponentAManager;
import com.example.component_base.interface_custom.IComponentBManager;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

public class MainActivity extends BaseActivity {
    private TextView textView;
    private Button bt_component_a;
    private Button bt_component_b;
    private Button bt_component_a_content;
    private Button bt_component_b_content;
    private Button bt_login;
    private Button bt_order;
    private Button bt_change_fragment;
    private Button bt_change_fragment_color;
    private Button bt_load_plugin;

    IComponentCallback printResultCallback = new IComponentCallback() {
        @Override
        public void onResult(CC cc, CCResult result) {
            if (!result.isSuccess()) {
                Toast.makeText(MainActivity.this, "跳转失败,code = " + result.getCode() +
                        ", description = " + result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            } else {
                String username = result.getDataItem(Global.KEY_USERNAME);
                Toast.makeText(MainActivity.this, "username: " + username, Toast.LENGTH_SHORT).show();
            }
        }
    };

    IComponentCallback fragmentTextUpdateCallback = new IComponentCallback() {
        @Override
        public void onResult(CC cc, CCResult result) {
            CCResult ccResult = null;
            Fragment fragment = result.getDataItem("Fragment");
            if (fragment != null) {
                ccResult = CC.obtainBuilder(ComponentConst.Component_view.NAME)
                        .setActionName(ComponentConst.Component_view.Action.CHANGEFRAGMENTTEXT) //action名称
                        .addParam("Fragment", result.getDataItem("Fragment")) //目标fragment对象
                        .addParam("newString", "恭喜你，修改成功") //设置参数
                        .build().call();
                if (!ccResult.isSuccess()) {
                    Toast.makeText(MainActivity.this, "跳转失败,code = " + ccResult.getCode() +
                            ", description = " + ccResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    IComponentCallback fragmentColorUpdateCallback = new IComponentCallback() {
        @Override
        public void onResult(CC cc, CCResult result) {
            if (!result.isSuccess()) {
                Toast.makeText(MainActivity.this, "跳转失败,code = " + result.getCode() +
                        ", description = " + result.getErrorMessage(), Toast.LENGTH_SHORT).show();
            } else {
                CCResult ccResult = null;
                ccResult = CC.obtainBuilder(ComponentConst.Component_view.NAME)
                        .setActionName(ComponentConst.Component_view.Action.CHANGEFRAGMENTCOLOR)
                        .addParam("Activity", result.getDataItem("Activity"))
                        .build()
                        .call();
                if (!ccResult.isSuccess()) {
                    Toast.makeText(MainActivity.this, "替换失败,code = " + ccResult.getCode() +
                            ", description = " + ccResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initData();
        //开启登录状态监听
        startListenLoginState();
        //...
    }

    @Override
    public void refreshLoginUser(String userId) {
        //开启登录监听时和登录状态改变时会调用此方法
        //demo: 在主线程Toast当前userId
        boolean isLogin = userId != null;
        Toast.makeText(this, "userId=" + userId
                + ", isLogin=" + isLogin, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isRefreshLoginUserRunOnMainThread() {
        //指定refreshLoginUser方法在主线程运行
        return true;
    }

    private void initUI() {
        textView = (TextView) findViewById(R.id.textView);
        bt_component_a = (Button) findViewById(R.id.bt_component_a);
        bt_component_b = (Button) findViewById(R.id.bt_component_b);
        bt_component_a_content = (Button) findViewById(R.id.bt_component_a_content);
        bt_component_b_content = (Button) findViewById(R.id.bt_component_b_content);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_order = (Button) findViewById(R.id.bt_order);
        bt_change_fragment = (Button) findViewById(R.id.bt_change_fragment);
        bt_change_fragment_color = (Button) findViewById(R.id.bt_change_fragment_color);
        bt_load_plugin = (Button) findViewById(R.id.bt_load_plugin);
    }

    private void initData() {
        bt_component_a_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CCResult ccResult = CC.obtainBuilder(ComponentConst.Component_A.NAME)
                        .setActionName(ComponentConst.Component_A.Action.GET)
                        .build()
                        .call();

                //是否获取成功
                if (ccResult.isSuccess()) {
                    IComponentAManager componentAManager = ccResult.getDataItemWithNoKey();
                    UserBean userBean = componentAManager.get();
                    if (userBean != null) {
                        textView.setText("name:" + userBean.getName() + "\n"
                                + "age:" + userBean.getAge() + "\n"
                                + "weight:" + userBean.getWeight());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "获取失败,code = " + ccResult.getCode() +
                            ", description = " + ccResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_component_b_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CCResult ccResult = CC.obtainBuilder(ComponentConst.Component_B.NAME)
                        .setActionName(ComponentConst.Component_B.Action.GET)
                        .build()
                        .call();

                //是否获取成功
                if (ccResult.isSuccess()) {
                    IComponentBManager componentBManager = ccResult.getDataItemWithNoKey();
                    UserBean userBean = componentBManager.get();
                    if (userBean != null) {
                        textView.setText("name:" + userBean.getName() + "\n"
                                + "age:" + userBean.getAge() + "\n"
                                + "weight:" + userBean.getWeight());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "获取失败,code = " + ccResult.getCode() +
                            ", description = " + ccResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_component_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CC cc = CC.obtainBuilder(ComponentConst.Component_A.NAME)
                        .setActionName("showComponentA")
                        .build();
                CCResult result = cc.call();
                if (!result.isSuccess()) {
                    Toast.makeText(MainActivity.this, "跳转失败,code = " + result.getCode() +
                            ", description = " + result.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_component_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CC cc = CC.obtainBuilder(ComponentConst.Component_B.NAME)
                        .setActionName("showComponentB")
                        .build();
                CCResult result = cc.call();
                if (!result.isSuccess()) {
                    Toast.makeText(MainActivity.this, "跳转失败,code = " + result.getCode() +
                            ", description = " + result.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CC cc = CC.obtainBuilder(ComponentConst.Component_login.NAME)
//                        .setActionName(ComponentConst.Component_login.Action.OPENLOGINACTIVITY)
//                        .build();
//                CCResult result = cc.call();
//                String username = result.getDataItem(Global.KEY_USERNAME);
//                if (!result.isSuccess()) {
//                    Toast.makeText(MainActivity.this, "跳转失败,code = " + result.getCode() +
//                            ", description = " + result.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
                //此处必须用异步，否则会报错-9，超时,因为LoginActivity因为特定的需求没有，立刻调用sendCCResult，
                //会导致CC框架内部阻塞等待CCResult，最终超时
                CC.obtainBuilder(ComponentConst.Component_login.NAME)
                        .setActionName(ComponentConst.Component_login.Action.OPENLOGINACTIVITY)
                        .build().callAsyncCallbackOnMainThread(printResultCallback);
            }
        });

        bt_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CC cc = CC.obtainBuilder(ComponentConst.Component_order.NAME)
//                        .setActionName(ComponentConst.Component_order.Action.OPENORDERACTIVITY)
//                        .build();
//                CCResult result = cc.call();
//                if (!result.isSuccess()) {
//                    Toast.makeText(MainActivity.this, "跳转失败,code = " + result.getCode() +
//                            ", description = " + result.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                }
                //此处必须用异步，否则会报错-9，超时,因为会走到LoginActivity的逻辑，LoginActivity因为特定的需求没有，立刻调用sendCCResult，
                //会导致CC框架内部阻塞等待CCResult，最终超时
                CC.obtainBuilder(ComponentConst.Component_order.NAME)
                        .setActionName(ComponentConst.Component_order.Action.OPENORDERACTIVITY)
                        .build().callAsyncCallbackOnMainThread(printResultCallback);
            }
        });

        bt_change_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CC.obtainBuilder(ComponentConst.Component_view.NAME)
                        .setActionName(ComponentConst.Component_view.Action.OPENVIEWACTIVITYANDWAITFRAGMENTCREATE)
                        .build()
                        .callAsyncCallbackOnMainThread(fragmentTextUpdateCallback);
            }
        });

        bt_change_fragment_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CC.obtainBuilder(ComponentConst.Component_view.NAME)
                        .setActionName(ComponentConst.Component_view.Action.OPENVIEWACTIVITY)
                        .build()
                        .callAsyncCallbackOnMainThread(fragmentColorUpdateCallback);
            }
        });

        bt_load_plugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 加载补丁
                try {
                    TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
