package com.zongbutech.httplib.http.Bean;

/**
 * Created by lixian on 2016/6/30.
 */
public class UserGlod extends BaseBean {


    /**
     * userId : 5770e87b9a237b960da02ea2
     * goldCoins : 94861
     * username : zongbu0
     * icon : http://nim.nos.netease.com/MTAzOTkyNQ==/bmltYV80NTA4MzEyMV8xNDY2MTY1MzkyNTM2X2RiZTJlOGVjLTgyN2ItNDNiMC04MWZkLTgxYjRmZDg1ZjFiMw==
     * gender : 1
     * role : 0
     * fansCount : 0
     * followingCount : 1
     * zhiwu : sfsf
     * cityId : 110100
     * brief : sfsfsf
     * articleCount : 0
     * diamond : 0
     */

    private String userId;
    private int goldCoins;
    private String username;
    private String icon;
    private int gender;
    private int role;
    private int fansCount;
    private int followingCount;
    private String zhiwu;
    private String cityId;
    private String brief;
    private int articleCount;
    private int diamond;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getGoldCoins() {
        return goldCoins;
    }

    public void setGoldCoins(int goldCoins) {
        this.goldCoins = goldCoins;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public String getZhiwu() {
        return zhiwu;
    }

    public void setZhiwu(String zhiwu) {
        this.zhiwu = zhiwu;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }
}
