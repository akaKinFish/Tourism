package com.example.rog.tourism.home.data.SpotDetails;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.home.data.SpotDetails
 * ROG
 * 2018/05/01/21:10
 * by KinFish
 * -------------------------------------------
 **/
public class ResultBean {
    /**
     * title : 景点活动预告
     * referral : 北京欢乐谷由"峡湾森林、爱琴港、失落玛雅、香格里拉、蚂蚁王国、亚特兰蒂斯、欢乐时光"等七大文化主题区组成，
     * 通过主题文化包装及故事演绎，以建筑、雕塑、园林、壁画、表演、游乐等多种形式，向游客展示了一个多姿多彩的地球生态环境与
     * 地域文化，园区内精选世界经典文明和创意智慧，精心设置了50余项主题景观、10余项主题表演、30多项主题游乐设施、20余项主题
     * 游戏及商业辅助设施，营造了一个神秘、梦幻的世界。30多万平方米的绿化、8万平方米的湖面赋予欢乐谷良好的生态环境；七大主题
     * 区域赋予欢乐谷独特的人文气质；国际国内双重标准的安全检测、人性化的服务配套和智能化的全园信息系统赋予欢乐谷世界一流的品质。
     * img : http://pic4.40017.cn/scenery/destination/2016/07/20/14/enL8Qz.jpg
     */

    private String title;
    private String referral;
    private String img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}