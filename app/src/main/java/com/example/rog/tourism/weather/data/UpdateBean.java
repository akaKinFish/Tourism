package com.example.rog.tourism.weather.data;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.cityselect.data.dto
 * ROG
 * 2018/04/25/10:32
 * by KinFish
 * -------------------------------------------
 **/
public  class UpdateBean {
    /**
     * loc : 2017-10-26 23:09
     * utc : 2017-10-26 15:09
     */

    private String loc;
    private String utc;

    @Override
    public String toString() {
        return "UpdateBean{" +
                "loc='" + loc + '\'' +
                ", utc='" + utc + '\'' +
                '}';
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }
}
