package com.zongbutech.ntfinance.session.extension;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhoujianghua on 2015/4/10.
 */
public class Lx2CustomAttachment extends CustomAttachment {


    public Lx2CustomAttachment() {
        super(CustomAttachmentType.Guess);
    }

    String value="null,Lx";

    @Override
    protected void parseData(JSONObject data) {
        value = data.get("value").toString();
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("value", "lx2");
        return data;
    }

    public String getContent() {
        return value;
    }

}
