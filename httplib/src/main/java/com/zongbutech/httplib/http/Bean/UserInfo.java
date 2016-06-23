package com.zongbutech.httplib.http.Bean;

import java.util.List;

/**
 * Created by lixian on 2016/6/23.
 */
public class UserInfo extends  BaseBean {


    /**
     * mobilePhone : 13282008515
     * username : qwe123
     * email : 576a5f8df2931@576a5f8df2937.com
     * id : 576a5f8ea957f19907c3b580
     * gold : 0
     * diamond : 0
     * nimToken : 9b3576c6035bd0a51607c954c4ff5505
     * friendIds : []
     * followingCount : 0
     * fansCount : 0
     * role : 0
     * ntfinanceUserId : 590
     * updatedAt : 2016-06-22T10:17:41.986Z
     */

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
}
