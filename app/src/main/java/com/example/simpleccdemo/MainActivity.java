package com.example.simpleccdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.example.component_base.ComponentConst;
import com.example.component_base.UserBean;
import com.example.component_base.interface_custom.IComponentAManager;
import com.example.component_base.interface_custom.IComponentBManager;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button bt_component_a;
    private Button bt_component_b;
    private Button bt_component_a_content;
    private Button bt_component_b_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initData();
    }

    private void initUI() {
        textView = (TextView) findViewById(R.id.textView);
        bt_component_a = (Button) findViewById(R.id.bt_component_a);
        bt_component_b = (Button) findViewById(R.id.bt_component_b);
        bt_component_a_content = (Button) findViewById(R.id.bt_component_a_content);
        bt_component_b_content = (Button) findViewById(R.id.bt_component_b_content);
    }

    private void initData() {
        bt_component_a_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CCResult ccResult = CC.obtainBuilder(ComponentConst.Component_A.NAME)
                        .setActionName(ComponentConst.Component_A.Action.SHOW)
                        .build()
                        .call();

                //是否获取成功
                if (ccResult.isSuccess()) {
                    IComponentAManager componentAManager = ccResult.getDataItemWithNoKey();
                    UserBean userBean = componentAManager.show();
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
                        .setActionName(ComponentConst.Component_B.Action.SHOW)
                        .build()
                        .call();

                //是否获取成功
                if (ccResult.isSuccess()) {
                    IComponentBManager componentBManager = ccResult.getDataItemWithNoKey();
                    UserBean userBean = componentBManager.show();
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
    }
}
