package com.zongbutech.httplib.http.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lixian on 2016/6/23.
 */
public class UserInfo extends  BaseBean  {




    private String mobilePhone;
    private String username;
    private String email;
    private String id;
    private int gold;
    private int diamond;
    private String nimToken;
    private int followingCount;
    private int fansCount;
    private int role;
    private int ntfinanceUserId;
    private String updatedAt;
    private List<String> friendIds;
    /**
     * gender : 1
     * icon : http://nim.nos.netease.com/MTAzOTkyNQ==/bmltYV80NTg1MTE5MF8xNDY2NDc2NDA2OTE5X2ZkNzcxYmQwLWVkNDctNGY0My05Y2I5LWRlMTVkZThmMzQ4NQ==
     * brief : i am a man
     * mingyan : 早上大跌要买，早上大涨要卖，下午大涨不追，下午大跌次日买，早上大跌不割。
     * zhiwu : sffsfsf
     * latestPredict : {"predict":1,"time":1467165239869}
     * cityId : 110100
     * provinceId : 120000
     */

    private int gender;
    private String icon;
    private String brief;
    private String mingyan;
    private String zhiwu;
    /**
     * predict : 1
     * time : 1467165239869
     */

    private LatestPredictBean latestPredict;
    private String cityId;
    private String provinceId;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public String getNimToken() {
        return nimToken;
    }

    public void setNimToken(String nimToken) {
        this.nimToken = nimToken;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getNtfinanceUserId() {
        return ntfinanceUserId;
    }

    public void setNtfinanceUserId(int ntfinanceUserId) {
        this.ntfinanceUserId = ntfinanceUserId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public List<String> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(List<String> friendIds) {
        this.friendIds = friendIds;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getMingyan() {
        return mingyan;
    }

    public void setMingyan(String mingyan) {
        this.mingyan = mingyan;
    }

    public String getZhiwu() {
        return zhiwu;
    }

    public void setZhiwu(String zhiwu) {
        this.zhiwu = zhiwu;
    }

    public LatestPredictBean getLatestPredict() {
        return latestPredict;
    }

    public void setLatestPredict(LatestPredictBean latestPredict) {
        this.latestPredict = latestPredict;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public static class LatestPredictBean  implements Serializable{
        private int predict;
        private long time;

        public int getPredict() {
            return predict;
        }

        public void setPredict(int predict) {
            this.predict = predict;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}
