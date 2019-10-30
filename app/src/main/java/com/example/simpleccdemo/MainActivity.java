package com.example.simpleccdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.example.component_base.ComponentConst;
import com.example.component_base.UserBean;
import com.example.component_base.interface_custom.IComponentAManager;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button bt_library_one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        bt_library_one = (Button) findViewById(R.id.bt_library_one);

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
        }

        bt_library_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CC cc = CC.obtainBuilder(ComponentConst.Component_A.NAME)
                        .setActionName("showActivityLibraryOne")
                        .build();
                CCResult result = cc.call();
            }
        });
    }
}
