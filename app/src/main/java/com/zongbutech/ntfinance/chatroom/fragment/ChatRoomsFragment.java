package com.zongbutech.ntfinance.chatroom.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.netease.nim.uikit.common.adapter.TAdapterDelegate;
import com.netease.nim.uikit.common.adapter.TViewHolder;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.common.ui.ptr.PullToRefreshBase;
import com.netease.nim.uikit.common.ui.ptr.PullToRefreshGridView;
import com.zongbutech.httplib.http.API.NtfinaceApi;
import com.zongbutech.httplib.http.Bean.HttpChatRoomBean;
import com.zongbutech.httplib.http.Utils.JsonUtils;
import com.zongbutech.httplib.http.Utils.OkHttpUtils;
import com.zongbutech.httplib.http.db.ChatRoomBean;
import com.zongbutech.httplib.http.db.ChatRoomBeanDao;
import com.zongbutech.ntfinance.R;
import com.zongbutech.ntfinance.chatroom.activity.ChatRoomActivity;
import com.zongbutech.ntfinance.chatroom.adapter.ChatRoomAdapter;
import com.zongbutech.ntfinance.chatroom.viewholder.ChatRoomViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播间列表fragment
 * Created by huangjun on 2015/12/11.
 */
public class ChatRoomsFragment extends TFragment implements TAdapterDelegate, ChatRoomAdapter.ViewHolderEventListener {

    private static final String TAG = ChatRoomsFragment.class.getSimpleName();
    private View loadingFrame;
    private PullToRefreshGridView gridView;
    private List<ChatRoomBean> items = new ArrayList<>();
    private ChatRoomAdapter adapter;

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public Class<? extends TViewHolder> viewHolderAtPosition(int position) {
        return ChatRoomViewHolder.class;
    }

    @Override
    public boolean enabled(int position) {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_rooms, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initAdapter();
        findViews();
    }

    public void onCurrent() {
        fetchData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void findViews() {
        // loading
        loadingFrame = findView(com.netease.nim.uikit.R.id.contact_loading_frame);
        loadingFrame.setVisibility(View.VISIBLE);

        gridView = findView(R.id.chat_room_grid_view);
        gridView.setAdapter(adapter);
        gridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                onFetchDataDone(false);
            }
        });
    }

    @Override
    public void onItemClick(ChatRoomBean bean) {
        ChatRoomActivity.start(getActivity(), bean.getNimRoomId());
    }

    private void initAdapter() {
        adapter = new ChatRoomAdapter(getActivity(), items, this, this);
    }

    private void fetchData() {

        OkHttpUtils.get(NtfinaceApi.getChatrooms, new OkHttpUtils.ResultCallback<JsonArray>() {
            @Override
            public void onSuccess(JsonArray mJsonArray) {

                List<ChatRoomBean> mChatRoomBeans = new ArrayList<ChatRoomBean>();
                for (int i = 0; i < mJsonArray.size(); i++) {
                    HttpChatRoomBean mHttpChatRoomBean = JsonUtils.deserialize(mJsonArray.get(i).toString(), HttpChatRoomBean.class);
                    ChatRoomBean mChatRoomBean = getChatRoomBean(mHttpChatRoomBean);
                    mChatRoomBeans.add(mChatRoomBean);
                }
                if (items.isEmpty()) {
                    items.addAll(mChatRoomBeans);
                }
                saveChatRoomBeans(items);
                onFetchDataDone(true);
            }

            @Override
            public void onFailure(Exception e) {
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "fetch chat room list failed," + e.getMessage(), Toast.LENGTH_SHORT);
                }
                List<ChatRoomBean> mChatRoomBeans = getChatRoomBeans();
                if (mChatRoomBeans != null && mChatRoomBeans.size() > 0) {
                    items.addAll(mChatRoomBeans);
                    onFetchDataDone(true);
                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "本地数据为空", Toast.LENGTH_SHORT);
                    }
                }

//                onFetchDataDone(false);
//                if (getActivity() != null) {
//                    Toast.makeText(getActivity(), "fetch chat room list failed," + e.getMessage(), Toast.LENGTH_SHORT);
//                }
//                Log.e(ChatRoomsFragment.class.getSimpleName(), "fetch chat room list failed," + e.getMessage());
            }
        });

    }

    private List<ChatRoomBean> getChatRoomBeans() {
        try {
            List<ChatRoomBean> mChatRoomBeans = mDaoSession.getChatRoomBeanDao().queryBuilder().list();
            if (mChatRoomBeans != null && mChatRoomBeans.size() > 0) {
                return mChatRoomBeans;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveChatRoomBeans(List<ChatRoomBean> items) {
        try {
            ChatRoomBeanDao chatRoomBeanDao = mDaoSession.getChatRoomBeanDao();
            chatRoomBeanDao.deleteAll();
            for (ChatRoomBean bean : items) {
                chatRoomBeanDao.insert(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    //得到id值
    private ChatRoomBean getChatRoomBeanById(String id) {
        try {
            ChatRoomBeanDao chatRoomBeanDao = mDaoSession.getChatRoomBeanDao();
            ChatRoomBean bean = chatRoomBeanDao.queryBuilder().where(ChatRoomBeanDao.Properties.ObjectId.eq(id)).list().get(0);
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //网络转化为本地
    private ChatRoomBean getChatRoomBean(HttpChatRoomBean mHttpChatRoomBean) {
        ChatRoomBean mChatRoomBean =   getChatRoomBeanById(mHttpChatRoomBean.id);
        if(mChatRoomBean ==null){
             mChatRoomBean = new ChatRoomBean();
        }
        mChatRoomBean.setCreatedAt(mHttpChatRoomBean.createdAt);
        mChatRoomBean.setCreatorId(mHttpChatRoomBean.creatorId);
        mChatRoomBean.setObjectId(mHttpChatRoomBean.id);
        mChatRoomBean.setHasPassword(mHttpChatRoomBean.hasPassword);
        mChatRoomBean.setIconURL(mHttpChatRoomBean.iconURL);
        mChatRoomBean.setMaxUser(mHttpChatRoomBean.maxUser);
        mChatRoomBean.setName(mHttpChatRoomBean.name);
        mChatRoomBean.setNimRoomId(mHttpChatRoomBean.nimRoomId);
        mChatRoomBean.setOwnerId(mHttpChatRoomBean.ownerId);
        mChatRoomBean.setPriority(mHttpChatRoomBean.priority);
        mChatRoomBean.setStatus(mHttpChatRoomBean.status);
        mChatRoomBean.setUpdatedAt(mHttpChatRoomBean.updatedAt);
        return mChatRoomBean;
    }


    private void onFetchDataDone(boolean success) {
        loadingFrame.setVisibility(View.GONE);
        gridView.onRefreshComplete();
        if (success) {
            refresh();
        }
    }

    private void refresh() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

}
