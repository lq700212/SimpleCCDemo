package com.example.component_b;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.component_base.UserBean;

public class ActivityB extends AppCompatActivity {

    private TextView textview;
    private Button bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_b);

        textview = (TextView) findViewById(R.id.textview);

        ComponentBManager componentBManager = ComponentBManager.getInstance();
        UserBean userBean = componentBManager.get();
        textview.setText("ComponentB:\nname:" + userBean.getName() + "\n"
                + "age:" + userBean.getAge() + "\n"
                + "weight:" + userBean.getWeight());

        bt_back = (Button) findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
