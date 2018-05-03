package com.example.rog.tourism;

import android.app.Application;

public class TourismApp extends Application {

    private static TourismApp context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        TourismInit.initTimber();
        TourismInit.initOkHttp(this);
    }

    public static TourismApp getContext() {
        return context;
    }
}
