package com.example.component_view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;

public class AFragment extends Fragment {
    private TextView tv_frag_a;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_a, container, false);
        tv_frag_a = (TextView) view.findViewById(R.id.tv_frag_a);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String callId = getActivity().getIntent().getStringExtra("component_view_frag");
        //判断是否为CC调用打开本页面
        if (callId != null) {
            CC.sendCCResult(callId, CCResult.success("Fragment", this));
        }
    }

    public void updateTextView(String newString) {
        tv_frag_a.setText(newString);
        tv_frag_a.setTextSize(40);
        tv_frag_a.setGravity(Gravity.CENTER);
    }

    @Override
    public View getView() {
        return view;
    }

}

