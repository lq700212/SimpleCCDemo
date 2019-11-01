package com.example.component_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.example.component_base.Global;

public class BaseActivity extends AppCompatActivity {

    private Button bt_change_fragment;
    private int changeFlag = 0;

    private String callId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initUI();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        callId = getIntent().getStringExtra("component_view");
        //判断是否为CC调用打开本页面
        if (callId != null) {
            CC.sendCCResult(callId, CCResult.success("Activity", this));
        }
    }

    private void initUI() {
        bt_change_fragment = (Button) findViewById(R.id.bt_change_fragment);
    }

    private void initData() {
        bt_change_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                if (changeFlag % 2 == 0) {
                    fragment = new BFragment();
                } else {
                    fragment = new AFragment();
                }
                replaceFragment(fragment);
                changeFlag++;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.commit();
    }

    public void replaceFramentWithC() {
        replaceFragment(new CFragment());
    }
}
