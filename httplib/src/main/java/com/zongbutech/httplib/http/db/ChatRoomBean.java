package com.zongbutech.httplib.http.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "CHAT_ROOM_BEAN".
 */
public class ChatRoomBean extends com.zongbutech.httplib.http.Bean.BaseBean  {

    private Long id;
    private java.util.Date createdAt;
    private String creatorId;
    private String iconURL;
    private Integer maxUser;
    private String name;
    private String nimRoomId;
    private String ownerId;
    private Integer priority;
    private Integer status;
    private java.util.Date updatedAt;

    public ChatRoomBean() {
    }

    public ChatRoomBean(Long id) {
        this.id = id;
    }

    public ChatRoomBean(Long id, java.util.Date createdAt, String creatorId, String iconURL, Integer maxUser, String name, String nimRoomId, String ownerId, Integer priority, Integer status, java.util.Date updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.creatorId = creatorId;
        this.iconURL = iconURL;
        this.maxUser = maxUser;
        this.name = name;
        this.nimRoomId = nimRoomId;
        this.ownerId = ownerId;
        this.priority = priority;
        this.status = status;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
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

    public Integer getMaxUser() {
        return maxUser;
    }

    public void setMaxUser(Integer maxUser) {
        this.maxUser = maxUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNimRoomId() {
        return nimRoomId;
    }

    public void setNimRoomId(String nimRoomId) {
        this.nimRoomId = nimRoomId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
