package com.zongbutech.httplib.http.Bean;

/**
 * Created by lixian on 2016/6/24.
 */
public class HttpChatRoomBean extends BaseBean {


    /**
     * createdAt : 2016-06-19T11:43:07.919Z
     * creatorId : 5763527af3941898452c7561
     * iconURL : http://112.124.0.91/Uploads/room/2016-06-02/574fa2d0ef4d2.png
     * id : 5766854b80ca16a72923c9be
     * maxUser : 200
     * name : 宗布聊天室
     * nimRoomId : 3373585
     * ownerId : 5766483f80ca16a72923c869
     * priority : 0
     * status : 1
     * updatedAt : 2016-06-19T11:43:07.919Z
     */

    private String createdAt;
    private String creatorId;
    private String iconURL;
    private String id;
    private int maxUser;
    private String name;
    private String nimRoomId;
    private String ownerId;
    private int priority;
    private int status;
    private String updatedAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxUser() {
        return maxUser;
    }

    public void setMaxUser(int maxUser) {
        this.maxUser = maxUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNimRoomId(String nimRoomId) {
        this.nimRoomId = nimRoomId;
    }

    public String getNimRoomId() {
        return nimRoomId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
