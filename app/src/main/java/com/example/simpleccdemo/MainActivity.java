package com.example.simpleccdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.example.component_base.ComponentConst;
import com.example.component_base.UserBean;
import com.example.component_base.interface_custom.IComponentAManager;

public class MainActivity extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);

        CCResult ccResult = CC.obtainBuilder(ComponentConst.Component_A.NAME)
                .setActionName(ComponentConst.Component_A.Action.SHOW)
                .build()
                .call();
        //是否获取成功
        if (ccResult.isSuccess()) {
            IComponentAManager componentAManager = ccResult.getDataItemWithNoKey();
            UserBean userBean = componentAManager.show();
            if (userBean != null) {
                tv.setText("name:" + userBean.getName() + "\n"
                        + "age:" + userBean.getAge() + "\n"
                        + "height:" + userBean.getHeight());
            }
        }
    }
}
