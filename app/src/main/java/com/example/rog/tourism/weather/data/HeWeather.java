package com.example.rog.tourism.weather.data;

import java.util.List;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.cityselect.data.dto
 * ROG
 * 2018/04/24/21:30
 * by KinFish
 * -------------------------------------------
 **/
public class HeWeather {
    private List<HeWeather6Bean> HeWeather6;

    @Override
    public String toString() {
        return "HeWether{" +
                "HeWeather6=" + HeWeather6 +
                '}';
    }

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }


}

