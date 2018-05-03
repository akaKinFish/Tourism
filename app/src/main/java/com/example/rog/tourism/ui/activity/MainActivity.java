package com.example.rog.tourism.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rog.tourism.R;
import com.example.rog.tourism.TourismConstant;
import com.example.rog.tourism.home.ui.HomeContentFragment;
import com.example.rog.tourism.ui.fragment.NewsFragment;
import com.example.rog.tourism.ui.fragment.SubwayFragment;
import com.example.rog.tourism.ui.fragment.WeatherFragment;
import com.example.rog.tourism.utils.PackageUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView tv_title;
    private ConstraintLayout constraintLayout;
    private FragmentManager mfragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mfragmentManager = getSupportFragmentManager();
        initView();
        initData();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        tv_title = navigationView.getHeaderView(0).findViewById(R.id.tv_nav_title);
        drawerLayout = findViewById(R.id.drawer_layout);
        constraintLayout = findViewById(R.id.constraint_content);
        setSupportActionBar(toolbar);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initData() {
        mfragmentManager.beginTransaction().replace(R.id.constraint_content,
                HomeContentFragment.newInstance(), TourismConstant.FG_HOME_CONTENT).commit();
        toolbar.setTitle("旅游通");
        String version = PackageUtils.packageName();
        if (version != null) {
            String msg = String.format("Tourism Version: %1$s", version);
            tv_title.setText(msg);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_see_home:
                if (mfragmentManager.findFragmentByTag(TourismConstant.FG_HOME_CONTENT) == null) {
                    mfragmentManager.beginTransaction().replace(R.id.constraint_content,
                            HomeContentFragment.newInstance(), TourismConstant.FG_HOME_CONTENT).commit();
                    toolbar.setTitle("旅游通");
                }
                break;
            case R.id.nav_see_news:
                if (mfragmentManager.findFragmentByTag(TourismConstant.FG_NEWS) == null) {
                    mfragmentManager.beginTransaction().replace(R.id.constraint_content,
                            NewsFragment.newInstance(), TourismConstant.FG_NEWS).commit();
                    toolbar.setTitle("看新闻");
                }
                break;
            case R.id.nav_use_check_weather:
                if (mfragmentManager.findFragmentByTag(TourismConstant.FG_WEATHER) == null) {
                    mfragmentManager.beginTransaction().replace(R.id.constraint_content,
                            WeatherFragment.newInstance(), TourismConstant.FG_WEATHER).commit();
                    toolbar.setTitle("查查天气");
                }
                break;
            case R.id.nav_use_check_subway:
                if (mfragmentManager.findFragmentByTag(TourismConstant.FG_SUBWAY) == null) {
                    mfragmentManager.beginTransaction().replace(R.id.constraint_content,
                            SubwayFragment.newInstance(), TourismConstant.FG_SUBWAY).commit();
                    toolbar.setTitle("查地铁");
                }
                break;
            case R.id.nav_else_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.nav_else_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

