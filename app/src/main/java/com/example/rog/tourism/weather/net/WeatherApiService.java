package com.example.rog.tourism.weather.net;

import com.example.rog.tourism.TourismInit;
import com.example.rog.tourism.weather.data.WeatherResult;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * Weather API请求接口
 *
 */
public class WeatherApiService {

    private static String BASE_URL = "https://free-api.heweather.com/s6/weather/";  //未使用

    public APIs apis;

    private static WeatherApiService instance;

    public static WeatherApiService getInstance(){
        if(instance == null){
            instance = new WeatherApiService();
        }
        return instance;
    }

    private WeatherApiService(){
        Retrofit storeRestAPI = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(TourismInit.mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apis = storeRestAPI.create(APIs.class);



    }

    public interface APIs{
        /*天气io*/

        @GET("forecast")
        Observable<WeatherResult> fetchWeather(
                @Query("location") String location,
                @Query("key") String key
        );



    }

}
