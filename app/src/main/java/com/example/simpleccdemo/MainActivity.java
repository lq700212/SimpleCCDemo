package com.example.simpleccdemo;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.didi.virtualapk.PluginManager;
import com.example.component_base.ComponentConst;
import com.example.component_base.Global;
import com.example.component_base.UserBean;
import com.example.component_base.interface_custom.IComponentAManager;
import com.example.component_base.interface_custom.IComponentBManager;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends BaseActivity {
    private TextView textView;
    private Button bt_component_a;
    private Button bt_component_b;
    private Button bt_component_a_content;
    private Button bt_component_b_content;
    private Button bt_login;
    private Button bt_order;

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

    private Button bt_change_fragment;
    private Button bt_change_fragment_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initData();
        //开启登录状态监听
        startListenLoginState();
        //...
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        setStatusBarTranslucent(this, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            }
        }
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
                if (PluginManager.getInstance(MainActivity.this).getLoadedPlugin("com.example.component_a") == null) {
                    String pluginPath = Environment.getExternalStorageDirectory().getAbsolutePath().concat("/component_a.apk");
                    File plugin = new File(pluginPath);
                    try {
                        PluginManager.getInstance(MainActivity.this).loadPlugin(plugin);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "load插件失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    CC cc = CC.obtainBuilder(ComponentConst.Component_A.NAME)
//                            .setActionName("showComponentA")
//                            .build();
//                    CCResult result = cc.call();
//                    if (!result.isSuccess()) {
//                        Toast.makeText(MainActivity.this, "跳转失败,code = " + result.getCode() +
//                                ", description = " + result.getErrorMessage(), Toast.LENGTH_SHORT).show();
//                    }
                    //测试直接以virtualApk的形式打开界面
                    try {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName("com.example.component_a", "com.example.component_a.ActivityA"));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
    }

    // 设置状态栏透明与字体颜色
    public static void setStatusBarTranslucent(Activity activity, boolean isLightStatusBar) {
        if (activity == null) return;
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        if (isXiaomi()) {
            setXiaomiStatusBar(window, isLightStatusBar);
        }
    }

    // 是否是小米手机
    public static boolean isXiaomi() {
        return "Xiaomi".equals(Build.MANUFACTURER);
    }

    // 设置小米状态栏
    public static void setXiaomiStatusBar(Window window, boolean isLightStatusBar) {
        Class<? extends Window> clazz = window.getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, isLightStatusBar ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
