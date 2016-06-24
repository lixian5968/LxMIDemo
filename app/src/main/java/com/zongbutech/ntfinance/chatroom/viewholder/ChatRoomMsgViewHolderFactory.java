package com.zongbutech.ntfinance.chatroom.viewholder;

import android.util.Log;

import com.netease.nim.uikit.session.viewholder.MsgViewHolderAudio;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderFactory;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderLocation;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderNotification;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderPicture;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderUnknown;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderVideo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomNotificationAttachment;
import com.netease.nimlib.sdk.msg.attachment.AudioAttachment;
import com.netease.nimlib.sdk.msg.attachment.ImageAttachment;
import com.netease.nimlib.sdk.msg.attachment.LocationAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.attachment.NotificationAttachment;
import com.netease.nimlib.sdk.msg.attachment.VideoAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.zongbutech.ntfinance.session.extension.GuessAttachment;
import com.zongbutech.ntfinance.session.extension.RewardAttachment;
import com.zongbutech.ntfinance.session.viewholder.MsgViewHolderTip;

import java.util.HashMap;

/**
 * 聊天室消息项展示ViewHolder工厂类。
 */
public class ChatRoomMsgViewHolderFactory {

    private static HashMap<Class<? extends MsgAttachment>, Class<? extends MsgViewHolderBase>> viewHolders = new HashMap<>();
    private static Class<? extends MsgViewHolderBase> tipMsgViewHolder;

    static {
        // built in
        register(ChatRoomNotificationAttachment.class, ChatRoomMsgViewHolderNotification.class);
        register(GuessAttachment.class, ChatRoomMsgViewHolderGuess.class);

        register(RewardAttachment.class, ChatRoomMsgViewHolderReward.class);

//        register(TipAttachment.class, MsgViewHolderTip.class);

        // built in
        register(ImageAttachment.class, MsgViewHolderPicture.class);
        register(AudioAttachment.class, MsgViewHolderAudio.class);
        register(VideoAttachment.class, MsgViewHolderVideo.class);
        register(LocationAttachment.class, MsgViewHolderLocation.class);
        register(NotificationAttachment.class, MsgViewHolderNotification.class);
    }

    public static void register(Class<? extends MsgAttachment> attach, Class<? extends MsgViewHolderBase> viewHolder) {
        viewHolders.put(attach, viewHolder);
    }

    public static Class<? extends MsgViewHolderBase> getViewHolderByType(IMMessage message) {
        if (message.getMsgType() == MsgTypeEnum.text) {
            return ChatRoomViewHolderText.class;
        } else if (message.getMsgType() == MsgTypeEnum.tip) {
            return MsgViewHolderTip.class;
        } else {
            Class<? extends MsgViewHolderBase> viewHolder = null;
            if (message.getAttachment() != null) {
                Class<? extends MsgAttachment> clazz = message.getAttachment().getClass();
                while (viewHolder == null && clazz != null) {
                    viewHolder = viewHolders.get(clazz);
                    if (viewHolder == null) {
                        clazz = MsgViewHolderFactory.getSuperClass(clazz);
                    }
                }
            }
            if( viewHolder == null ){
                Log.e("","");
            }
            return viewHolder == null ? MsgViewHolderUnknown.class : viewHolder;
        }
    }

    public static int getViewTypeCount() {
        // plus text and unknown
        return viewHolders.size() + 2;
    }
}
