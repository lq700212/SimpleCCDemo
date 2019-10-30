package com.example.library_one;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.component_base.UserBean;

public class ActivityA extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        textView = (TextView) findViewById(R.id.textView);

        ComponentAManager componentAManager = ComponentAManager.getInstance();
        UserBean userBean = componentAManager.show();
        textView.setText("ComponentA:\nname:" + userBean.getName() + "\n"
                +"age:" + userBean.getAge() + "\n"
                + "weight:" + userBean.getWeight());
    }
}
