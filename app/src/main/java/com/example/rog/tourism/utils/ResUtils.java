package com.example.rog.tourism.utils;

import com.example.rog.tourism.TourismApp;

/**
 * 获取文件资源工具类
 *
 */
public class ResUtils {
    /*获取文件资源*/
    public static String getString(int strID) {
        return TourismApp.getContext().getResources().getString(strID);

    }
}
