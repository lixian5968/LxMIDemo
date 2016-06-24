package com.zongbutech.ntfinance.session.action;

import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.zongbutech.ntfinance.R;


/**
 * Tip类型消息测试
 * Created by hzxuwen on 2016/3/9.
 */
public class LxAction extends BaseAction {

    public LxAction() {
        super(R.drawable.message_plus_tip_selector, R.string.input_lx_message);
    }

    @Override
    public void onClick() {


        IMMessage message = MessageBuilder.createTextMessage(
                getAccount(), // 聊天对象的 ID，如果是单聊，为用户帐号，如果是群聊，为群组 ID
                getSessionType(), // 聊天类型，单聊或群组
                "自定义文本消息" // 文本内容
        );
// 发送消息。如果需要关心发送结果，可设置回调函数。发送完成时，会收到回调。如果失败，会有具体的错误码。
        sendMessage(message);

    }

}
