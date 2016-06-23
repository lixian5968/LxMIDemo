package com.zongbutech.httplib.http.Bean;

import java.io.Serializable;

/**
 * Created by lixian on 2016/6/23.
 */
public class ConfigsBean implements Serializable{


    /**
     * code : mobile-login
     * createdAt : 2016-05-19T08:42:41.084Z
     * expire : 50
     * id : 573d7c8127e3b12574c06350
     * name : 手机验证码登录配置
     * updatedAt : 2016-05-19T08:42:41.084Z
     * value : {"id":"573d708327e3b12574c06348","model":"Server","type":"ntfinance","appKey":"bc33374e1148db528e032777ac36557e","appSecret":"0ead11fc40fb"}
     */

    private String code;
    private String createdAt;
    private int expire;
    private String id;
    private String name;
    private String updatedAt;
    /**
     * id : 573d708327e3b12574c06348
     * model : Server
     * type : ntfinance
     * appKey : bc33374e1148db528e032777ac36557e
     * appSecret : 0ead11fc40fb
     */

    private ValueBean value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

    public static class ValueBean {
        private String id;
        private String model;
        private String type;
        private String appKey;
        private String appSecret;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }
    }
}
