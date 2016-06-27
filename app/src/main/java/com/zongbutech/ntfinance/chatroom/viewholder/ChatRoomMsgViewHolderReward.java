package com.zongbutech.ntfinance.chatroom.viewholder;


import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.uikit.session.viewholder.MsgViewHolderText;
import com.zongbutech.ntfinance.session.extension.RewardAttachment;

/**
 * Created by hzxuwen on 2016/1/20.
 */
public class ChatRoomMsgViewHolderReward extends MsgViewHolderText {

//    @Override
//    protected String getDisplayText() {
//        RewardAttachment attachment = (RewardAttachment) message.getAttachment();
//        return attachment.getValue();
//    }




    protected TextView notificationTextView;
    RelativeLayout message_item_notification_label_all;


    @Override
    protected int getContentResId() {
        return com.netease.nim.uikit.R.layout.nim_message_item_notification;
    }

    @Override
    protected void inflateContentView() {
        notificationTextView = (TextView) view.findViewById(com.netease.nim.uikit.R.id.message_item_notification_label);
        message_item_notification_label_all = (RelativeLayout) view.findViewById(com.netease.nim.uikit.R.id.message_item_notification_label_all);

    }

    @Override
    protected void bindContentView() {
        message_item_notification_label_all.setVisibility(View.VISIBLE);
        RewardAttachment attachment = (RewardAttachment) message.getAttachment();
        notificationTextView.setText(attachment.getValue());
    }

    @Override
    protected boolean isMiddleItem() {
        return true;
    }




}
