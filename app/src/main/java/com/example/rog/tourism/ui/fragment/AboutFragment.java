package com.example.rog.tourism.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rog.tourism.R;
import com.example.rog.tourism.utils.PackageUtils;
import com.example.rog.tourism.utils.ResUtils;

/**
 * 关于的fragment
 */

public class AboutFragment extends Fragment{

    private TextView abfr_tv_app_version;

    public static AboutFragment newInstance() {

        AboutFragment fragment = new AboutFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,container,false);
        abfr_tv_app_version = view.findViewById(R.id.abfr_tv_app_version);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String version = PackageUtils.packageName();
        if(version != null){
            String msg = String.format(ResUtils.getString(R.string.app_version), version);
            abfr_tv_app_version.setText(msg);
        }
    }
}
