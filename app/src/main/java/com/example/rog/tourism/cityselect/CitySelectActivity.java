package com.example.rog.tourism.cityselect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.example.rog.tourism.R;
import com.example.rog.tourism.TourismApp;
import com.example.rog.tourism.cityselect.adapter.CityListAdapter;
import com.example.rog.tourism.cityselect.adapter.ResultListAdapter;
import com.example.rog.tourism.cityselect.data.China_Cities;
import com.example.rog.tourism.cityselect.data.DBManager;
import com.example.rog.tourism.cityselect.utils.LocateState;
import com.example.rog.tourism.cityselect.utils.StringUtils;
import com.example.rog.tourism.cityselect.view.SlideLetterBar;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.cityselect
 * ROG
 * 2018/04/30/16:58
 * by KinFish
 * -------------------------------------------
 **/
public class CitySelectActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_PICKED_CITY = "picked_city";
    public static final String CODE_PICKED_CITY = "citycode";

    private ListView mListView;
    private ListView mResultListView;
    private SlideLetterBar mLetterBar;
    private EditText mSearchEditText;
    private ImageView mClearImageView;
    private ImageView mBackImageView;
    private ViewGroup mEmptyView;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<China_Cities> mAllCities;
    private DBManager dbManager;

    private AMapLocationClient mLocationClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cityselect_activity_city_list);
        Logger.v("tag", File.separator + "data"
                + Environment.getDataDirectory().getAbsolutePath() + File.separator
                + TourismApp.getContext().getPackageName() + File.separator + "databases" + File.separator);
        
        initData();
        initView();
        initLocation();
    }
    /*初始化定位*/
    private void initLocation() {
//        //声明mLocationOption对象
//        public AMapLocationOption mLocationOption = null;
////初始化定位参数
//        mLocationOption = new AMapLocationClientOption();
////设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
////设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
//        //设置是否只定位一次,默认为false
//        mLocationOption.setOnceLocation(false);
////设置是否强制刷新WIFI，默认为强制刷新
//        mLocationOption.setWifiActiveScan(true);
////设置是否允许模拟位置,默认为false，不允许模拟位置
//        mLocationOption.setMockEnable(false);
////设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
////给定位客户端对象设置定位参数
//        mlocationClient.setLocationOption(mLocationOption);
//        //启动定位
//        mlocationClient.startLocation();
        mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        /*高精度定位*/
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        /*仅定位一次*/
        option.setOnceLocation(true);
        /*允许模拟定位*/
        option.setMockEnable(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(aMapLocation -> {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    String city = aMapLocation.getCity();
                    String district = aMapLocation.getDistrict();
                    String location = StringUtils.extractLocation(city, district);//市县名称筛选
                    mCityAdapter.updateLocateState(LocateState.SUCCESS, location);
                } else {
                    //定位失败

                    Logger.e(aMapLocation.getErrorInfo()+" "+ aMapLocation.getErrorCode());
                    mCityAdapter.updateLocateState(LocateState.FAILED, null);
                }
            }
        });
        mLocationClient.startLocation();


    }

    private void initData() {
        dbManager = new DBManager(this);
        //dbManager.copyDBFiles();
        mAllCities = dbManager.getAllCities();
        System.out.println(mAllCities.get(0).getCityname());
        mCityAdapter = new CityListAdapter(this,mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String cityname) {
                back(cityname);
            }

            @Override
            public void onLocateClick() {
                mCityAdapter.updateLocateState(LocateState.LOCATING,null);
                mLocationClient.startLocation();

            }
        });
        mResultAdapter = new ResultListAdapter(this,null);

    }

    /**
     * 初始化视图
     */
    private void initView() {
        mListView = findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);

        TextView overlay = findViewById (R.id.tv_letter_overlay);
        mLetterBar = findViewById(R.id.sild_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setmOnLetterChangedListener(letter -> {
            int position = mCityAdapter.getLetterPosition(letter);//获取到letter所在的位置
            mListView.setSelection(position);
        });

        mSearchEditText = findViewById(R.id.et_search);
        //文字框输入监听
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    mClearImageView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    mClearImageView.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<China_Cities> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    } else {
                        mEmptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        mEmptyView = findViewById(R.id.empty_view);
        mResultListView = findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> back(mResultAdapter.getItem(position).getCityname()));

        mClearImageView = findViewById(R.id.iv_search_clear);
        mBackImageView = findViewById(R.id.back);

        mClearImageView.setOnClickListener(this);
        mBackImageView.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        //if(SpUtils.getBoolean(getApplicationContext(), SpUtils.FIRST_START, true))
        super.onBackPressed();

    }

    private void back(String cityname){
        Intent data = new Intent();
        dbManager = new DBManager(this);

        data.putExtra(KEY_PICKED_CITY, cityname);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_search_clear) {
            mSearchEditText.setText("");
            mClearImageView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.GONE);
            mResultListView.setVisibility(View.GONE);
        } else if (i == R.id.back) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();
    }
}
