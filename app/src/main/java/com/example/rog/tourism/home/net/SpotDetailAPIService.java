package com.example.rog.tourism.home.net;

import com.example.rog.tourism.TourismInit;
import com.example.rog.tourism.home.data.SpotDetails.ResultBean;
import com.example.rog.tourism.home.data.SpotDetails.SpotDetailsResult;


import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.home.net
 * ROG
 * 2018/05/02/16:24
 * by KinFish
 * -------------------------------------------
 **/
public class SpotDetailAPIService {
    private static String BASE_URL = "http://apis.haoservice.com/lifeservice/travel/";

    public SpotDetailsAPIs spotDetailsAPIs;

    private static  SpotDetailAPIService instance;

    public static SpotDetailAPIService getInstance(){
        if(instance == null){
            instance = new SpotDetailAPIService();
        }
        return  instance;
    }

    private SpotDetailAPIService() {
        Retrofit storeAPI = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(TourismInit.mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        spotDetailsAPIs = storeAPI.create(SpotDetailsAPIs.class);
    }

    public interface SpotDetailsAPIs{
//        key=424f608ec9a843af8a88fdd45d01ca37&sid=29828


        @GET("GetScenery")
        Observable<SpotDetailsResult> fetchSpotDetails(
                @Query("sid") String sid,
                @Query("key") String key
        );


    }

}
