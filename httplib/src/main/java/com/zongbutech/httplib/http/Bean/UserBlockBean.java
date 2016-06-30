package com.zongbutech.httplib.http.Bean;

import java.util.Date;

/**
 * Created by lixian on 2016/6/28.
 */
public class UserBlockBean extends BaseBean {


    /**
     * id : 577216ab7acaff85035d73ff
     * createdAt : 2016-06-28T06:18:19.234Z
     * updatedAt : 2016-06-28T06:18:19.243Z
     * userId : 5770e8d89a237b960da02eac
     * blockUserId : 5770e8859a237b960da02ea9
     * roomId : 3436139
     * name : qwe123
     * avatar : http://112.124.0.91/Uploads/header/1.png
     */

    private String id;
    private Date createdAt;
    private Date updatedAt;
    private String userId;
    private String blockUserId;
    private String roomId;
    private String name;
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBlockUserId() {
        return blockUserId;
    }

    public void setBlockUserId(String blockUserId) {
        this.blockUserId = blockUserId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
