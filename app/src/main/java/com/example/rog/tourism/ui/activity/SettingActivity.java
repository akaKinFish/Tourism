package com.example.rog.tourism.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rog.tourism.R;
import com.example.rog.tourism.ui.fragment.SettingFragment;
import com.r0adkll.slidr.Slidr;

import java.util.Objects;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.ui.activity
 * ROG
 * 设置的activity
 * 2018/04/26/11:16
 * by KinFish
 * -------------------------------------------
 **/
public class SettingActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        /*左滑退出activity*/
        Slidr.attach(this);
        initData();
        initView();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());

        getSupportFragmentManager().beginTransaction().replace(R.id.cly_root, SettingFragment.newInstance()).commit();

    }

    private void initData() {
    }
}
