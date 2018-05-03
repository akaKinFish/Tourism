package com.example.rog.tourism.cityselect.utils;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.cityselect.utils
 * ROG
 * 2018/04/30/16:30
 * by KinFish
 * -------------------------------------------
 **/
public class StringUtils {
    /**
     * 提取出城市或者县
     * @param city
     * @param district
     * @return
     */
    public static String extractLocation(final String city, final String district){
        return district.contains("县") ? district.substring(0, district.length() - 1) : city.substring(0, city.length() - 1);
    }
}