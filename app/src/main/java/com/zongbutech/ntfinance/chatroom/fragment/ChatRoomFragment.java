package com.zongbutech.ntfinance.chatroom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.nim.uikit.cache.SimpleCallback;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.ui.imageview.ImageViewEx;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.constant.MemberQueryType;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.zongbutech.httplib.http.Utils.ImageLoaderUtils;
import com.zongbutech.ntfinance.R;
import com.zongbutech.ntfinance.chatroom.activity.ChatRoomActivity;
import com.zongbutech.ntfinance.chatroom.adapter.ChatRoomTabPagerAdapter;
import com.zongbutech.ntfinance.chatroom.fragment.tab.ChatRoomTabFragment;
import com.zongbutech.ntfinance.chatroom.helper.ChatRoomHelper;
import com.zongbutech.ntfinance.chatroom.helper.ChatRoomMemberCache;
import com.zongbutech.ntfinance.common.ui.viewpager.FadeInOutPageTransformer;
import com.zongbutech.ntfinance.common.ui.viewpager.PagerSlidingTabStrip;

import java.util.List;

/**
 * 聊天室顶层fragment
 * Created by hzxuwen on 2015/12/14.
 */
public class ChatRoomFragment extends ChatRoomTabFragment implements ViewPager.OnPageChangeListener {
    private static final String TAG = ChatRoomFragment.class.getSimpleName();
    private PagerSlidingTabStrip tabs;
    private ViewPager viewPager;
    private ChatRoomTabPagerAdapter adapter;
    private int scrollState;
    private ImageViewEx imageView;
    private TextView chatRoomName;
    private TextView statusText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onInit() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_room_fragment, container, false);
    }

    ChatRoomInfo mChatRoomInfo;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findViews();
        setupPager();
        setupTabs();
    }

    public void updateOnlineStatus(boolean isOnline) {
        statusText.setVisibility(isOnline ? View.GONE : View.VISIBLE);
    }

    public void updateView() {
        mChatRoomInfo = ((ChatRoomActivity) getActivity()).getRoomInfo();
        chatRoomName.setText(mChatRoomInfo.getName());
        getUsers();


        ChatRoomHelper.setCoverImage(((ChatRoomActivity) getActivity()).getRoomInfo().getRoomId(), imageView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    TextView message_black;
    LinearLayout user_v;

    private void findViews() {
        imageView = findView(R.id.chat_room_view);
        statusText = findView(R.id.online_status);
        chatRoomName = findView(R.id.chatRoomName);
        message_black = findView(R.id.message_black);
        user_v = findView(R.id.user_v);


        final ImageView backImage = findView(R.id.back_arrow);
        tabs = findView(R.id.chat_room_tabs);
        viewPager = findView(R.id.chat_room_viewpager);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NIMClient.getService(ChatRoomService.class).exitChatRoom(((ChatRoomActivity) getActivity()).getRoomInfo().getRoomId());
                ((ChatRoomActivity) getActivity()).clearChatRoom();
            }
        });

        message_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsers();
            }
        });
    }

    public void getUsers() {
        user_v.removeAllViews();
        ChatRoomMemberCache.getInstance().fetchRoomMembers(mChatRoomInfo.getRoomId(), MemberQueryType.NORMAL, 0, 100, new SimpleCallback<List<ChatRoomMember>>() {
            @Override
            public void onResult(boolean success, List<ChatRoomMember> result) {
                if (success) {
                    for (ChatRoomMember bean : result) {
                        if (bean.getExtension() != null && bean.getExtension().get("role") != null) {
                            int role = Integer.parseInt(bean.getExtension().get("role") + "");
                            if (role > 0 && bean.getAvatar()!=null && bean.getAvatar().length()>0) {
                                HeadImageView headImageView = new HeadImageView(getActivity());
                                headImageView.loadBuddyAvatar(bean.getAvatar());
                                user_v.addView(headImageView);
                            }
                        }
                    }
                }
            }
        });
        ChatRoomMemberCache.getInstance().fetchRoomMembers(mChatRoomInfo.getRoomId(), MemberQueryType.GUEST, 0, 100, new SimpleCallback<List<ChatRoomMember>>() {
            @Override
            public void onResult(boolean success, List<ChatRoomMember> result) {
                if (success) {
                    for (ChatRoomMember bean : result) {
                        if (bean.getExtension() != null && bean.getExtension().get("role") != null) {
                            int role = Integer.parseInt(bean.getExtension().get("role") + "");
                            if(role>0){

                                HeadImageView imageView = new HeadImageView(getActivity());
                                ImageLoaderUtils.display(getActivity(),imageView,bean.getAvatar());
                                user_v.addView(imageView);


//                                HeadImageView headImageView = new HeadImageView(getActivity());
//                                headImageView.loadBuddyAvatar(bean.getAvatar());
//                                user_v.addView(headImageView);
                            }
                        }
                    }
                }
            }
        });
    }

    private void setupPager() {
        // CACHE COUNT
        adapter = new ChatRoomTabPagerAdapter(getFragmentManager(), getActivity(), viewPager);
        viewPager.setOffscreenPageLimit(adapter.getCacheCount());
        // page swtich animation
        viewPager.setPageTransformer(true, new FadeInOutPageTransformer());
        // ADAPTER
        viewPager.setAdapter(adapter);
        // TAKE OVER CHANGE
        viewPager.setOnPageChangeListener(this);
    }

    private void setupTabs() {
        tabs.setOnCustomTabListener(new PagerSlidingTabStrip.OnCustomTabListener() {
            @Override
            public int getTabLayoutResId(int position) {
                return R.layout.chat_room_tab_layout;
            }

            @Override
            public boolean screenAdaptation() {
                return true;
            }
        });
        tabs.setViewPager(viewPager);
        tabs.setOnTabClickListener(adapter);
        tabs.setOnTabDoubleTapListener(adapter);
    }

    /********************
     * OnPageChangeListener
     **************************/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // TO TABS
        tabs.onPageScrolled(position, positionOffset, positionOffsetPixels);
        // TO ADAPTER
        adapter.onPageScrolled(position);
    }

    @Override
    public void onPageSelected(int position) {
        // TO TABS
        tabs.onPageSelected(position);

        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // TO TABS
        tabs.onPageScrollStateChanged(state);

        scrollState = state;

        selectPage(viewPager.getCurrentItem());
    }

    private void selectPage(int page) {
        // TO PAGE
        if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
            adapter.onPageSelected(viewPager.getCurrentItem());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.getItem(0).onActivityResult(requestCode, resultCode, data);
    }
}
