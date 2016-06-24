package com.zongbutech.ntfinance.chatroom.viewholder;

import android.view.View;
import android.widget.TextView;

import com.netease.nim.uikit.common.adapter.TViewHolder;
import com.netease.nim.uikit.common.ui.imageview.ImageViewEx;
import com.zongbutech.httplib.http.Bean.HttpChatRoomBean;
import com.zongbutech.httplib.http.db.ChatRoomBean;
import com.zongbutech.ntfinance.R;
import com.zongbutech.ntfinance.chatroom.adapter.ChatRoomAdapter;

public class ChatRoomViewHolder extends TViewHolder {
    private final static int COUNT_LIMIT = 10000;
    private ImageViewEx coverImage;
    private TextView nameText;
    private TextView onlineCountText;

    private ChatRoomBean room;

    public void refresh(Object item) {
        room = (ChatRoomBean) item;
        updateBackground();
//        ChatRoomHelper.setCoverImage(room.getNimRoomId(), coverImage);
        coverImage.load(room.getIconURL());
        nameText.setText(room.getName());
        setOnlineCount();
    }

    protected int getResId() {
        return R.layout.chat_room_item;
    }

    public void inflate() {
        this.coverImage = findView(R.id.cover_image);
        this.nameText = findView(R.id.tv_name);
        this.onlineCountText = findView(R.id.tv_online_count);

        coverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChatRoomAdapter)getAdapter()).getEventListener().onItemClick(room);
            }
        });
    }

    private void updateBackground() {
        view.setBackgroundResource(R.drawable.list_item_bg_selecter);
    }

    private void setOnlineCount() {
//        if (room.getOnlineUserCount() < COUNT_LIMIT) {
//            onlineCountText.setText(String.valueOf(room.getOnlineUserCount()));
//        } else if (room.getOnlineUserCount() >= COUNT_LIMIT) {
//            onlineCountText.setText(String.format("%.1f", room.getOnlineUserCount() / (float) COUNT_LIMIT) + "万");
//        }

        onlineCountText.setText("不知道人数");
    }
}
