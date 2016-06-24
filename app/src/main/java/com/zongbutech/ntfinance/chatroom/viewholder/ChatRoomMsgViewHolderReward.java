package com.zongbutech.ntfinance.chatroom.viewholder;


import com.zongbutech.ntfinance.session.extension.RewardAttachment;

/**
 * Created by hzxuwen on 2016/1/20.
 */
public class ChatRoomMsgViewHolderReward extends ChatRoomViewHolderText {

    @Override
    protected String getDisplayText() {
        RewardAttachment attachment = (RewardAttachment) message.getAttachment();
        return attachment.getValue();
    }
}
