package com.zongbutech.ntfinance.chatroom.viewholder;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;
import com.zongbutech.ntfinance.chatroom.helper.ChatRoomNotificationHelper;

public class ChatRoomMsgViewHolderNotification extends MsgViewHolderBase {

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
//        message_item_notification_label_all.setVisibility(View.GONE);
        notificationTextView.setText(ChatRoomNotificationHelper.getNotificationText((ChatRoomNotificationAttachment) message.getAttachment()));
    }

    @Override
    protected boolean isMiddleItem() {
        return true;
    }
}

