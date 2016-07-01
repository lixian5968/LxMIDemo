package com.zongbutech.ntfinance.chatroom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.netease.nim.uikit.cache.SimpleCallback;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.ui.imageview.ImageViewEx;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.constant.MemberQueryType;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.zongbutech.httplib.http.Bean.OrderBean;
import com.zongbutech.httplib.http.Bean.UserGlod;
import com.zongbutech.httplib.http.Bean.UserInfo;
import com.zongbutech.httplib.http.Utils.SharePrefUtil;
import com.zongbutech.ntfinance.LxAdd.GoldPopupWindow;
import com.zongbutech.ntfinance.LxAdd.SendVPopupWindow;
import com.zongbutech.ntfinance.R;
import com.zongbutech.ntfinance.chatroom.activity.ChatRoomActivity;
import com.zongbutech.ntfinance.chatroom.adapter.ChatRoomTabPagerAdapter;
import com.zongbutech.ntfinance.chatroom.fragment.tab.ChatRoomTabFragment;
import com.zongbutech.ntfinance.chatroom.fragment.tab.MessageTabFragment;
import com.zongbutech.ntfinance.chatroom.helper.ChatRoomHelper;
import com.zongbutech.ntfinance.chatroom.helper.ChatRoomMemberCache;
import com.zongbutech.ntfinance.common.ui.viewpager.FadeInOutPageTransformer;
import com.zongbutech.ntfinance.common.ui.viewpager.PagerSlidingTabStrip;
import com.zongbutech.ntfinance.session.extension.RewardAttachment;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
//                getUsers();
            }
        });
    }

    List<ChatRoomMember> UserIcons = new ArrayList<>();

    public void AddOrRemoveUsers(boolean result, ChatRoomMember mChatRoomMember) {
        if (result) {
            AddUserIcon(mChatRoomMember);
        } else {
            int index = UserIcons.indexOf(mChatRoomMember);
            if (index != -1) {
                UserIcons.remove(index);
                user_v.removeViewAt(index);
            }
        }
    }

    public void getUsers() {
        UserIcons.clear();
        user_v.removeAllViews();
        ChatRoomMemberCache.getInstance().fetchRoomMembers(mChatRoomInfo.getRoomId(), MemberQueryType.NORMAL, 0, 100, new SimpleCallback<List<ChatRoomMember>>() {
            @Override
            public void onResult(boolean success, List<ChatRoomMember> result) {
                if (success) {
                    AddUserIcons(result);
                }
            }
        });
        ChatRoomMemberCache.getInstance().fetchRoomMembers(mChatRoomInfo.getRoomId(), MemberQueryType.GUEST, 0, 100, new SimpleCallback<List<ChatRoomMember>>() {
            @Override
            public void onResult(boolean success, List<ChatRoomMember> result) {
                if (success) {
                    AddUserIcons(result);
                }
            }
        });
    }

    private void AddUserIcons(List<ChatRoomMember> result) {
        for (ChatRoomMember bean : result) {
            if (bean.getExtension() != null && bean.getExtension().get("role") != null) {
                int role = Integer.parseInt(bean.getExtension().get("role") + "");
                if (role > 0 && bean.getAvatar() != null && bean.getAvatar().length() > 0) {
                    AddUserIcon(bean);
                }
            }
        }
    }

    SendVPopupWindow mSendVPopupWindow;
    ChatRoomMember mChatRoomMember;

    private void AddUserIcon(ChatRoomMember bean) {
        UserIcons.add(bean);
        HeadImageView imageView = new HeadImageView(getActivity());
        int w = (int) getActivity().getResources().getDimension(R.dimen.text_size_40);
        int h = (int) getActivity().getResources().getDimension(R.dimen.text_size_40);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(w, h);
        imageView.setLayoutParams(params);
        imageView.loadBuddyAvatar(bean.getAccount());
        imageView.setTag(bean.getAccount());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChatRoomMember = getChatRoomMemberById(v.getTag() + "");
                if (mChatRoomMember != null) {
                    mSendVPopupWindow = new SendVPopupWindow(getActivity(), onClickListener);
                    mSendVPopupWindow.setNameAndIcon(mChatRoomMember.getNick(), mChatRoomMember.getAccount());
                    mSendVPopupWindow.showAtLocation(viewPager, Gravity.CENTER, 0, 0);
                }
            }
        });
        user_v.addView(imageView);
    }

    GoldPopupWindow mGoldPopupWindow;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.send_it:
                    if (mSendVPopupWindow != null) {
                        mSendVPopupWindow.dismiss();
                        getMoney();
                    }
                    break;
                case R.id.call_it:
                    try {
                        ((MessageTabFragment)adapter.getItem(0)).getFragment().setText("@"+mChatRoomMember.getNick());
                        mSendVPopupWindow.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case R.id.guanzhu:
                    Toast.makeText(getActivity(), "guanzhu", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.gold_100:
                    if (mGoldPopupWindow != null) {
                        mGoldPopupWindow.dismiss();
                        sendMoney(Gold100);
                    }
                    break;
            }
        }
    };

    public static final int Gold100 = 100;

    private void sendMoney(int type) {
        if (type == Gold100) {
            String YangToken = SharePrefUtil.getString(ct, "YangToken", "");
            String userId = SharePrefUtil.getString(ct, "userId", "");
            JsonObject mJsonObject = new JsonObject();
            mJsonObject.addProperty("amount", 100);
            mJsonObject.addProperty("currency", "gold");
            mJsonObject.addProperty("toUserId", mChatRoomMember.getAccount());
            mJsonObject.addProperty("type", "transfer");
            mJsonObject.addProperty("fromUserId", userId);
            mJsonObject.addProperty("from", userId);
            mNtfinaceApi.sendUserGlod(YangToken, mJsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Action1<OrderBean>() {
                                @Override
                                public void call(OrderBean bean) {
                                    if ("done".equals(bean.state)) {
                                        Toast.makeText(ct, "打赏成功", Toast.LENGTH_SHORT).show();
                                        UserInfo mUserInfo = (UserInfo) SharePrefUtil.getObj(ct, "UserInfo");
                                        String username = "XXX";
                                        if (mUserInfo != null) {
                                            username = mUserInfo.getUsername();
                                        }
                                        String messageString = username + "打赏" + mChatRoomMember.getNick() + " [100金币]";
                                        RewardAttachment attachment = new RewardAttachment(messageString);
                                        IMMessage message = MessageBuilder.createCustomMessage(mChatRoomInfo.getRoomId(), SessionTypeEnum.ChatRoom, messageString, attachment);
                                        NIMClient.getService(ChatRoomService.class).sendMessage((ChatRoomMessage) message, true);
                                        try {
                                            ((MessageTabFragment)adapter.getItem(0)).getFragment().addMessgae(message);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Toast.makeText(ct, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

        }
    }

    //获取金额
    private void getMoney() {
        String userId = SharePrefUtil.getString(ct, "userId", "");
        String YangToken = SharePrefUtil.getString(ct, "YangToken", "");
        mNtfinaceApi.getUserGlod(userId, "updatedAt DESC", YangToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<UserGlod>() {
                            @Override
                            public void call(UserGlod mUserGlod) {
                                String s = "金币:" + mUserGlod.getGoldCoins() + ",钻石:" + mUserGlod.getDiamond();
                                mGoldPopupWindow = new GoldPopupWindow(getActivity(), onClickListener);
                                mGoldPopupWindow.setText(s);
                                mGoldPopupWindow.showAtLocation(viewPager, Gravity.CENTER, 0, 0);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Toast.makeText(ct, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

    }

    private ChatRoomMember getChatRoomMemberById(String s) {
        for (ChatRoomMember bean : UserIcons) {
            if (s.equals(bean.getAccount())) {
                return bean;
            }
        }
        return null;
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
