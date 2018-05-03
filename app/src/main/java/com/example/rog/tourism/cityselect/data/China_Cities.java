package com.example.rog.tourism.cityselect.data;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.cityselect.data
 * ROG
 * china_cities bean类
 * 2018/04/30/11:05
 * by KinFish
 * -------------------------------------------
 **/
public class China_Cities {
    private String citycode;
    private String pinyin;
    private String cityname;

    public China_Cities(){

    }

    public China_Cities(String citycode, String pinyin, String cityname) {
        this.citycode = citycode;
        this.pinyin = pinyin;
        this.cityname = cityname;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
