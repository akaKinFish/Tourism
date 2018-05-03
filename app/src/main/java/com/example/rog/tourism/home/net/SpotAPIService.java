package com.example.rog.tourism.home.net;


import com.example.rog.tourism.TourismInit;
import com.example.rog.tourism.home.data.ViewSpot.ViewSpotResult;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.home.net
 * ROG
 * 2018/05/02/9:07
 * by KinFish
 * -------------------------------------------
 **/
public class SpotAPIService  {

    private static final String BASE_URL ="http://apis.haoservice.com/lifeservice/travel/";

    public SpotAPIs spotAPIs;

    private static SpotAPIService instance;

    public  static SpotAPIService getInstance(){
        if (instance == null){
            instance = new SpotAPIService();
        }
        return instance;
    }

    private SpotAPIService(){
        Retrofit stroRestAPI = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(TourismInit.mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        spotAPIs = stroRestAPI.create(SpotAPIs.class);
    }



    public interface SpotAPIs{

        @GET("scenery")
        Observable<ViewSpotResult> fetchViewSpot(
                @Query("page") int page,
                @Query("key") String key
        );

    }
}
