package com.zongbutech.ntfinance.session.extension;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by zhoujianghua on 2015/4/9.
 */
public class RewardAttachment extends CustomAttachment {

    public RewardAttachment() {
        super(CustomAttachmentType.type);
    }

    public RewardAttachment(String s) {
        super(CustomAttachmentType.type);
        value= s;
    }


    String value ="";
    @Override
    protected void parseData(JSONObject data) {
        value = data.getString("value");
    }

    public String getValue() {
        return value;
    }

    @Override
    protected JSONObject packData() {
        JSONObject data = new JSONObject();
        data.put("value", value);
        return data;
    }


}
