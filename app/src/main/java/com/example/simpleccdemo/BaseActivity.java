package com.example.simpleccdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements LoginObserverDyComponent.ILoginObserver {
    private LoginObserverDyComponent loginObserverDyComponent;
    //申请两个权限，文件读和写
    //1、首先声明一个数组permissions，将需要的权限都放在里面
    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //2、创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
    List<String> mPermissionList = new ArrayList<>();

    private final int mRequestCode = 100;//权限请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //activity被销毁时
        // 如果监听了监听登录状态，需要注销对应的动态组件
        stopListenLoginState();
    }

    @Override
    public void refreshLoginUser(String userId) {
        //用户登录状态监听的回调
        //调用addLoginObserver()之后会立即获得一次当前登录用户id
        //若未登录，userId = null
        //若已登录，userId ≠ null
    }

    @Override
    public boolean isRefreshLoginUserRunOnMainThread() {
        //指定refreshLoginUser(userId)方法是否在主线程运行
        return false;
    }

    /**
     * 为当前activity添加登录状态监听
     * 通过{@link #refreshLoginUser(String)} 回调登录状态
     */
    protected void startListenLoginState() {
        if (loginObserverDyComponent == null) {
            loginObserverDyComponent = new LoginObserverDyComponent(this);
        }
        loginObserverDyComponent.start();
    }

    protected void stopListenLoginState() {
        if (loginObserverDyComponent != null) {
            loginObserverDyComponent.stop();
        }
    }

    //权限判断和申请
    private void initPermission() {

        mPermissionList.clear();//清空没有通过的权限

        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        }else{
            //说明权限都已经通过，可以做你想做的事情去
        }
    }

    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            }else{
                //全部权限通过，可以进行下一步操作。。。
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
