package com.example.rog.tourism.home.data.ViewSpot;

import java.util.ArrayList;
import java.util.List;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.home.data.ViewSpot
 * ROG
 * 2018/05/01/21:07
 * by KinFish
 * -------------------------------------------
 **/
public class ViewSpotResult {

    /**
     * error_code : 0
     * reason : 成功
     * result : [{"title":"北京欢乐谷","grade":"","price_min":"118","comm_cnt":null,"cityId":"","address":"北京市朝阳区东四环四方桥
     * 东南角","sid":"29828","url":"http://www.ly.com/scenery/BookSceneryTicket_29828.html","imgurl":"http://pic4.40017.cn/scenery/
     * destination/2017/02/28/19/imf23a_240x135_00.jpg"},{"title":"北京故宫","grade":"AAAAA","price_min":"5","comm_cnt":null,"cityId
     * ":"","address":"北京市景山前街4号","sid":"3007","url":"http://www.ly.com/scenery/BookSceneryTicket_3007.html","imgurl":"http:/
     * /pic4.40017.cn/scenery/destination/2016/07/13/16/aNWerO_240x135_00.jpg"},{"title":"十三陵","grade":"AAAAA","price_min":"42","co
     * mm_cnt":null,"cityId":"","address":"北京市昌平区长陵路明十三陵景区","sid":"3150","url":"http://www.ly.com/scenery/BookSceneryTic
     * ket_3150.html","imgurl":"http://pic4.40017.cn/scenery/destination/2017/02/26/21/GkE4eS_240x135_00.jpg"},{"title":"颐和园","grad
     * e":"AAAAA","price_min":"56","comm_cnt":null,"cityId":"","address":"北京市海淀区新建宫门路19号","sid":"3023","url":"http://www.l
     * y.com/scenery/BookSceneryTicket_3023.html","imgurl":"http://pic4.40017.cn/scenery/destination/2016/07/21/14/Yrr1Aj_240x135_00.j
     * pg"},{"title":"太平洋海底世界博览馆","grade":"AAA","price_min":"68","comm_cnt":null,"cityId":"","address":"北京市海淀区西三环中
     * 路11号中央广播电视塔下","sid":"5072","url":"http://www.ly.com/scenery/BookSceneryTicket_5072.html","imgurl":"http://pic5.40017.
     * cn/01/001/57/87/rBANC1oANB6ACKcMAAJUaOoJVaU484_240x135_00.jpg"},{"title":"水立方嬉水乐园","grade":"","price_min":"168","comm_cn
     * t":null,"cityId":"","address":"北京市朝阳区北辰路奥林匹克公园内","sid":"28757","url":"http://www.ly.com/scenery/BookSceneryTick
     * et_28757.html","imgurl":"http://pic4.40017.cn/scenery/destination/2016/07/26/11/84l1h1_240x135_00.jpg"},{"title":"中央电视塔","g
     * rade":"AAAA","price_min":"45","comm_cnt":null,"cityId":"","address":"北京市海淀区西三环中路11号","sid":"7930","url":"http://www.
     * ly.com/scenery/BookSceneryTicket_7930.html","imgurl":"http://pic4.40017.cn/scenery/destination/2016/07/28/14/mvoMn7_240x135_00.j
     * pg"},{"title":"居庸关长城","grade":"AAAA","price_min":"37","comm_cnt":null,"cityId":"","address":"北京市区50余公里外的昌平县境内
     * 南口镇居庸关村","sid":"3640","url":"http://www.ly.com/scenery/BookSceneryTicket_3640.html","imgurl":"http://pic4.40017.cn/scener
     * y/destination/2017/02/13/15/hxradu_240x135_00.jpg"},{"title":"京东石林峡","grade":"AAAA","price_min":"68","comm_cnt":null,"cityId
     * ":"","address":"北京近郊平谷区黄松峪乡雕窝村73号","sid":"17241","url":"http://www.ly.com/scenery/BookSceneryTicket_17241.html","
     * imgurl":"http://pic4.40017.cn/scenery/destination/2016/07/27/19/2Awplr_240x135_00.jpg"},{"title":"大运河森林公园绿岛乐园","grade
     * ":"","price_min":"89","comm_cnt":null,"cityId":"","address":"北京通州区宋梁路南段","sid":"139122","url":"http://www.ly.com/scener
     * y/BookSceneryTicket_139122.html","imgurl":"http://pic4.40017.cn/scenery/destination/2016/08/04/20/6NJ2g9_240x135_00.jpg"}]
     */

    private int error_code;
    private String reason;
    private ArrayList<ResultBean> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ArrayList<ResultBean> getResult() {
        return result;
    }

    public void setResult(ArrayList<ResultBean> result) {
        this.result = result;
    }


}
