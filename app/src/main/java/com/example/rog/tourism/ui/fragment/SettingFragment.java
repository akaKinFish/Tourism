package com.example.rog.tourism.ui.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rog.tourism.R;
import com.example.rog.tourism.utils.PackageUtils;
import com.example.rog.tourism.utils.ResUtils;

public class SettingFragment extends Fragment {

    private TextView tv_app_version;

      public static SettingFragment newInstance() {


        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        tv_app_version = view.findViewById(R.id.tv_app_version);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String version = PackageUtils.packageName();
        if(version != null){
            String msg = String.format(ResUtils.getString(R.string.cur_version),version);
            tv_app_version.setText(msg);

        }
    }
}
