package com.zongbutech.ntfinance.chatroom.adapter;

import android.content.Context;

import com.netease.nim.uikit.common.adapter.TAdapter;
import com.netease.nim.uikit.common.adapter.TAdapterDelegate;
import com.zongbutech.httplib.http.db.ChatRoomBean;

import java.util.List;

public class ChatRoomAdapter extends TAdapter {

    public ChatRoomAdapter(Context context, List<?> items, TAdapterDelegate delegate, ViewHolderEventListener
            viewHolderEventListener) {
        super(context, items, delegate);

        this.viewHolderEventListener = viewHolderEventListener;
    }

    public interface ViewHolderEventListener {
        void onItemClick(ChatRoomBean bean);
    }

    private ViewHolderEventListener viewHolderEventListener;

    public ViewHolderEventListener getEventListener() {
        return viewHolderEventListener;
    }
}
