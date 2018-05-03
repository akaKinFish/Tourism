package com.example.rog.tourism.weather.data;

import java.util.List;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.cityselect.data.dto
 * ROG
 * 2018/04/25/10:29
 * by KinFish
 * -------------------------------------------
 **/
public  class HeWeather6Bean {
    /**
     * basic : {"cid":"CN101010100","location":"北京","parent_city":"北京","admin_area":"北京","cnty":"中国","lat":"39.90498734","lon":"116.40528870","tz":"8.0"}
     * daily_forecast : [{"cond_code_d":"103","cond_code_n":"101","cond_txt_d":"晴间多云","cond_txt_n":"多云","date":"2017-10-26","hum":"57","pcpn":"0.0","pop":"0","pres":"1020","tmp_max":"16","tmp_min":"8","uv_index":"3","vis":"16","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"5"},{"cond_code_d":"101","cond_code_n":"501","cond_txt_d":"多云","cond_txt_n":"雾","date":"2017-10-27","hum":"56","pcpn":"0.0","pop":"0","pres":"1018","tmp_max":"18","tmp_min":"9","uv_index":"3","vis":"20","wind_deg":"187","wind_dir":"南风","wind_sc":"微风","wind_spd":"6"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2017-10-28","hum":"26","pcpn":"0.0","pop":"0","pres":"1029","tmp_max":"17","tmp_min":"5","uv_index":"2","vis":"20","wind_deg":"2","wind_dir":"北风","wind_sc":"3-4","wind_spd":"19"}]
     * status : ok
     * update : {"loc":"2017-10-26 23:09","utc":"2017-10-26 15:09"}
     */

    private BasicBean basic;
    private String status;
    private UpdateBean update;
    private List<DailyForecastBean> daily_forecast;

    @Override
    public String toString() {
        return "HeWeather6Bean{" +
                "basic=" + basic +
                ", status='" + status + '\'' +
                ", update=" + update +
                ", daily_forecast=" + daily_forecast +
                '}';
    }

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public List<DailyForecastBean> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

}
