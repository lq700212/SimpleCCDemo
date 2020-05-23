package com.example.simpleccdemo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity implements LoginObserverDyComponent.ILoginObserver {
    private LoginObserverDyComponent loginObserverDyComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
