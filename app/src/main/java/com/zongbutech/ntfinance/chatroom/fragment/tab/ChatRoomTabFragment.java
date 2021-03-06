package com.zongbutech.ntfinance.chatroom.fragment.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.nim.uikit.BaseApplication;
import com.netease.nim.uikit.common.fragment.TabFragment;
import com.zongbutech.httplib.http.API.NtfinaceApi;
import com.zongbutech.ntfinance.R;
import com.zongbutech.ntfinance.chatroom.constant.ChatRoomTab;

/**
 * Created by hzxuwen on 2015/12/14.
 */
public abstract class ChatRoomTabFragment extends TabFragment {
    private boolean loaded = false;

    private ChatRoomTab tabData;

    protected abstract void onInit();

    protected boolean inited() {
        return loaded;
    }

    public NtfinaceApi mNtfinaceApi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_tab_fragment_container, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BaseApplication app = (BaseApplication) getActivity().getApplicationContext();
        mNtfinaceApi = app.mNtfinaceApi;
    }

    public void attachTabData(ChatRoomTab tabData) {
        this.tabData = tabData;
    }

    @Override
    public void onCurrent() {
        super.onCurrent();

        if (!loaded && loadRealLayout()) {
            loaded = true;
            onInit();
        }
    }

    private boolean loadRealLayout() {
        ViewGroup root = (ViewGroup) getView();
        if (root != null) {
            root.removeAllViewsInLayout();
            View.inflate(root.getContext(), tabData.layoutId, root);
        }
        return root != null;
    }
}
