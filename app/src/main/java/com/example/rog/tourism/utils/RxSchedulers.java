package com.example.rog.tourism.utils;

import com.example.rog.tourism.R;
import com.example.rog.tourism.TourismApp;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.utils
 * ROG
 * Rx处理工具类
 * 2018/04/25/17:23
 * by KinFish
 * -------------------------------------------
 **/
public class RxSchedulers {
    public static <T> ObservableTransformer<T, T> compose() {
        return upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }

    public static void processRequestException(Throwable e) {
        if(e instanceof ConnectException || e instanceof SocketException) {
            ToastUtils.showShort(TourismApp.getContext(),ResUtils.getString(R.string.network_connected_exception));
        } else if(e instanceof SocketTimeoutException) {
            ToastUtils.showShort(TourismApp.getContext(),ResUtils.getString(R.string.network_socket_time_out));
        } else if(e instanceof JsonSyntaxException) {
            ToastUtils.showShort(TourismApp.getContext(),ResUtils.getString(R.string.network_json_syntax_exception));
        } else if(e instanceof UnknownHostException) {
            ToastUtils.showShort(TourismApp.getContext(),ResUtils.getString(R.string.network_unknown_host));
        } else {
            Timber.d(e.getMessage());
        }
    }
}
