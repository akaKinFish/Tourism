package com.example.rog.tourism.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rog.tourism.R;
import com.example.rog.tourism.TourismApp;
import com.example.rog.tourism.cityselect.CitySelectActivity;
import com.example.rog.tourism.cityselect.data.DBManager;
import com.example.rog.tourism.weather.data.HeWeather;
import com.example.rog.tourism.weather.data.HeWeather6Bean;
import com.example.rog.tourism.weather.net.WeatherApiService;
import com.example.rog.tourism.utils.RxSchedulers;
import com.example.rog.tourism.utils.SharedHelper;
import com.example.rog.tourism.utils.ToastUtils;

import java.util.List;
import com.orhanobut.logger.Logger;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.ui.fragment
 * ROG
 * 2018/04/24/15:38
 * by KinFish
 * -------------------------------------------
 **/
public class WeatherFragment extends Fragment {

    private static final String TAG = "WeatherFragment";
    private SwipeRefreshLayout weather_refresh;
    private CompositeDisposable mSubscriptions;
    private HeWeather heWeather;
    private final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private TextView city_name_Tv,cityTv,timeTv,humidityTv,weekTv,temperatureTv,climateTv,windTv;
    private SharedHelper sh;
    private String location = "CN101010100";
    private String key = "6cc706043fad47e783a6bde7c092c368";
    private String cityname = "北京";
    private ImageView title_city_manager;
    private static final int REQUEST_CODE_PICKED_CITY = 1 ;


    public static WeatherFragment newInstance(){
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather,container,false);
        title_city_manager = view.findViewById(R.id.title_city_manager);
        weather_refresh = view.findViewById(R.id.weather_refresh);
        city_name_Tv = view.findViewById(R.id.title_city_name);
        cityTv = view.findViewById(R.id.city);
        timeTv = view.findViewById(R.id.time);
        humidityTv = view.findViewById(R.id.humidity);
        weekTv = view.findViewById(R.id.week_today);
        temperatureTv = view.findViewById(R.id.temperature);
        climateTv = view.findViewById(R.id.climate);
        windTv = view.findViewById(R.id.wind);

        city_name_Tv.setText("N/A");
        cityTv.setText("N/A");
        timeTv.setText("N/A");
        humidityTv.setText("N/A");
        weekTv.setText("N/A");
        temperatureTv.setText("N/A");
        climateTv.setText("N/A");
        windTv.setText("N/A");
        Log.v("tag","执行");


        weather_refresh.setOnRefreshListener(() -> fetchHeWeather(true));
        title_city_manager.setOnClickListener(view1 -> selectCity(true));


        return view;
    }

    private void selectCity(boolean isSelect) {
        startActivityForResult(new Intent(getActivity(), CitySelectActivity.class),REQUEST_CODE_PICKED_CITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            location = data.getStringExtra(CitySelectActivity.KEY_PICKED_CITY);
            Logger.v("cityname",cityname);}
            fetchHeWeather(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubscriptions = new CompositeDisposable();
        heWeather = new HeWeather();
        weather_refresh.setRefreshing(true);
        weather_refresh.setEnabled(true);
        fetchHeWeather(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }
    /*拉取数据*/
    private void fetchHeWeather(boolean isRefresh) {
        Log.v("tag","执行3");

        Disposable subscribe = WeatherApiService.getInstance().apis.fetchWeather(location,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> {weather_refresh.setRefreshing(true);
                Log.v("tag","执行4");})
                .doOnComplete(() -> {weather_refresh.setRefreshing(false);
                Log.v("tag","执行5");})
                .subscribe((data) ->{
                    if(data.getHeWeather6() == null) Log.v("tag","result为空");
                    if(data !=null){
                        Log.v("tag","执行1");
                         List<HeWeather6Bean> heWeather6 = data.getHeWeather6();

                        if(isRefresh) {
                            Log.v("tag","执行2");

                            String cityName = heWeather6.get(0).getBasic().getLocation() + "天气";
                            String city = heWeather6.get(0).getBasic().getLocation();
                            /*发布时间*/
                            String loc = heWeather6.get(0).getUpdate().getLoc() + "发布";
                            /*湿度*/
                            String hum = "湿度" + heWeather6.get(0).getDaily_forecast().get(0).getHum()+"%";
                            String date = heWeather6.get(0).getDaily_forecast().get(0).getDate();
                            String temperature = heWeather6.get(0).getDaily_forecast().get(0).getTmp_min()
                                    + "～" + heWeather6.get(0).getDaily_forecast().get(0).getTmp_max()
                                    + "℃";
                            /*天气状况*/
                            String cond_txt_d = heWeather6.get(0).getDaily_forecast().get(0).getCond_txt_d();
                            String wind_sc = "风力"+heWeather6.get(0).getDaily_forecast().get(0).getWind_sc()+"级";

                            city_name_Tv.setText(cityName);
                            cityTv.setText(city);
                            timeTv.setText(loc);
                            humidityTv.setText(hum);
                            weekTv.setText(date);
                            temperatureTv.setText(temperature);
                            climateTv.setText(cond_txt_d);
                            windTv.setText(wind_sc);


                            ToastUtils.showShort(TourismApp.getContext(), "刷新成功！！！！！！");
                        }else {
                            ToastUtils.showShort(TourismApp.getContext(),"无法刷新");
                        }
                    }
                },RxSchedulers::processRequestException);
                mSubscriptions.add(subscribe);
    }

}
