package com.zongbutech.ntfinance.session.action;

import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.zongbutech.ntfinance.R;


/**
 * Tip类型消息测试
 * Created by hzxuwen on 2016/3/9.
 */
public class LxLocationAction extends BaseAction {

    private final static String TAG = "LocationAction";

    public LxLocationAction() {
        super(R.drawable.message_plus_tip_selector, R.string.input_lx_loca);
    }

    @Override
    public void onClick() {
        IMMessage message = MessageBuilder.createLocationMessage(getAccount(), getSessionType(), 188.320468, 30.480902,
                "不知道");
        sendMessage(message);
    }

}
