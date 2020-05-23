package com.example.component_a;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.example.component_base.ComponentConst;
import com.example.component_base.UserBean;
import com.example.component_base.interface_custom.IComponentBManager;

public class ActivityA extends AppCompatActivity {

    private TextView textView;
    private Button component_a_button;
    private Button bt_back;
    private Button bt_component_b_content_read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        initUI();
        initData();
    }

    private void initUI() {
        textView = (TextView) findViewById(R.id.textView);
        component_a_button = (Button) findViewById(R.id.component_a_button);
        bt_back = (Button) findViewById(R.id.bt_back);
        bt_component_b_content_read = (Button)findViewById(R.id.bt_component_b_content_read);
    }

    private void initData() {
        ComponentAManager componentAManager = ComponentAManager.getInstance();
        UserBean userBean = componentAManager.get();
        textView.setText("ComponentA:\nname:" + userBean.getName() + "\n"
                + "age:" + userBean.getAge() + "\n"
                + "weight:" + userBean.getWeight());

        component_a_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CC cc = CC.obtainBuilder(ComponentConst.Component_B.NAME)
                        .setActionName("showComponentB")
                        .build();
                CCResult result = cc.call();
                if (!result.isSuccess()) {
                    Toast.makeText(ActivityA.this, "跳转失败,code = " + result.getCode() +
                            ", description = " + result.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bt_component_b_content_read.setOnClickListener(new View.OnClickListener() {
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
                        Toast.makeText(ActivityA.this, "name:" + userBean.getName() + "\n" +
                                "age:" + userBean.getAge() + "\n" +
                                "weight:" + userBean.getWeight(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityA.this, "获取失败,code = " + ccResult.getCode() +
                            ", description = " + ccResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
