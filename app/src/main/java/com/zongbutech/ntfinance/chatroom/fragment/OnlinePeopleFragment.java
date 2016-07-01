package com.zongbutech.ntfinance.chatroom.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.netease.nim.uikit.cache.SimpleCallback;
import com.netease.nim.uikit.common.adapter.TAdapter;
import com.netease.nim.uikit.common.adapter.TAdapterDelegate;
import com.netease.nim.uikit.common.adapter.TViewHolder;
import com.netease.nim.uikit.common.fragment.TFragment;
import com.netease.nim.uikit.common.ui.dialog.CustomAlertDialog;
import com.netease.nim.uikit.common.ui.ptr.PullToRefreshBase;
import com.netease.nim.uikit.common.ui.ptr.PullToRefreshListView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.constant.MemberQueryType;
import com.netease.nimlib.sdk.chatroom.constant.MemberType;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.MemberOption;
import com.zongbutech.httplib.http.Bean.UserBlockBean;
import com.zongbutech.httplib.http.Utils.SharePrefUtil;
import com.zongbutech.ntfinance.DemoCache;
import com.zongbutech.ntfinance.R;
import com.zongbutech.ntfinance.chatroom.activity.ChatRoomActivity;
import com.zongbutech.ntfinance.chatroom.helper.ChatRoomMemberCache;
import com.zongbutech.ntfinance.chatroom.viewholder.OnlinePeopleViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 聊天室在线人数fragment
 * Created by hzxuwen on 2015/12/17.
 */
public class OnlinePeopleFragment extends TFragment implements TAdapterDelegate {
    private static final String TAG = OnlinePeopleFragment.class.getSimpleName();
    private static final int LIMIT = 100;

    private PullToRefreshListView listView;
    private TextView onlineText;
    private TAdapter<ChatRoomMember> adapter;
    private List<ChatRoomMember> items = new ArrayList<>();
    private String roomId;
    private Map<String, ChatRoomMember> memberCache = new ConcurrentHashMap<>();
    private long updateTime = 0; // 非游客的updateTime
    private long enterTime = 0; // 游客的enterTime

    private boolean isNormalEmpty = false; // 固定成员是否拉取完

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.online_people_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initAdapter();
        findViews();
        registerObservers(true);
    }

    public void onCurrent() {
        clearCache();
        roomId = ((ChatRoomActivity) getActivity()).getRoomInfo().getRoomId();
        fetchData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registerObservers(false);
    }

    private void clearCache() {
        updateTime = 0;
        enterTime = 0;
        items.clear();
        memberCache.clear();
        isNormalEmpty = false;
    }

    private void initAdapter() {
        adapter = new TAdapter<>(getActivity(), items, this);
    }

    private void findViews() {
        onlineText = findView(R.id.no_online_people);
        listView = findView(R.id.chat_room_online_list);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                fetchData();
            }
        });

        listView.setOnItemLongClickListener(longClickListener); // 线上入口屏蔽成员操作
    }

    private void stopRefreshing() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.onRefreshComplete();
            }
        }, 50);
    }

    /***************************
     * TAdapterDelegate
     **************************/
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public Class<? extends TViewHolder> viewHolderAtPosition(int position) {
        return OnlinePeopleViewHolder.class;
    }

    @Override
    public boolean enabled(int position) {
        return true;
    }

    private void fetchData() {
        if (!isNormalEmpty) {
            // 拉取固定在线成员
            getMembers(MemberQueryType.ONLINE_NORMAL, updateTime, 0);
        } else {
            // 拉取非固定成员
            getMembers(MemberQueryType.GUEST, enterTime, 0);
        }

        getLimitMembers(MemberQueryType.NORMAL, updateTime, 0);

    }

    /**
     * 获取成员列表
     */
    private void getMembers(final MemberQueryType memberQueryType, final long time, int limit) {
        ChatRoomMemberCache.getInstance().fetchRoomMembers(roomId, memberQueryType, time, (LIMIT - limit), new SimpleCallback<List<ChatRoomMember>>() {
            @Override
            public void onResult(boolean success, List<ChatRoomMember> result) {
                if (success) {
                    if (onlineText.getVisibility() == View.VISIBLE || result == null || result.isEmpty()) {
                        onlineText.setVisibility(View.GONE);
                    }
                    addMembers(result);
                    if (memberQueryType == MemberQueryType.ONLINE_NORMAL && result.size() < LIMIT) {
                        isNormalEmpty = true; // 固定成员已经拉完
                        getMembers(MemberQueryType.GUEST, enterTime, result.size());
                    }
                }
                stopRefreshing();
            }
        });
    }

    private void getLimitMembers(final MemberQueryType memberQueryType, final long time, int limit) {
        ChatRoomMemberCache.getInstance().fetchRoomMembers(roomId, memberQueryType, time, (LIMIT - limit), new SimpleCallback<List<ChatRoomMember>>() {
            @Override
            public void onResult(boolean success, List<ChatRoomMember> result) {
                if (success) {
                    if (onlineText.getVisibility() == View.VISIBLE || result == null || result.isEmpty()) {
                        onlineText.setVisibility(View.GONE);
                    }
                    List<ChatRoomMember> newResult = new ArrayList<ChatRoomMember>();
                    for (ChatRoomMember mChatRoomMember : result) {
                        if (mChatRoomMember.getMemberType() == MemberType.LIMITED) {
                            newResult.add(mChatRoomMember);
                        }
                    }
                    addMembers(newResult);
                }
                stopRefreshing();
            }
        });
    }


    private void addMembers(List<ChatRoomMember> members) {
        for (ChatRoomMember member : members) {
            if (!isNormalEmpty) {
                updateTime = member.getUpdateTime();
            } else {
                enterTime = member.getEnterTime();
            }

            if (memberCache.containsKey(member.getAccount())) {
                items.remove(memberCache.get(member.getAccount()));
            }
            memberCache.put(member.getAccount(), member);

            items.add(member);
        }
        Collections.sort(items, comp);
        adapter.notifyDataSetChanged();
    }

    /**
     * *************************** 成员操作监听 ****************************
     */
    private void registerObservers(boolean register) {
        ChatRoomMemberCache.getInstance().registerRoomMemberChangedObserver(roomMemberChangedObserver, register);
    }

    ChatRoomMemberCache.RoomMemberChangedObserver roomMemberChangedObserver = new ChatRoomMemberCache.RoomMemberChangedObserver() {
        @Override
        public void onRoomMemberIn(ChatRoomMember member) {
            Log.e("","");
        }

        @Override
        public void onRoomMemberExit(ChatRoomMember member) {
            Log.e("","");
        }
    };


    /****************************
     * 长按菜单
     *********************************/
    AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            fetchMemberInfo((ChatRoomMember) parent.getAdapter().getItem(position));
            return true;
        }
    };

    // 弹出菜单前，获取成员最新数据
    private void fetchMemberInfo(final ChatRoomMember member) {
        ChatRoomMemberCache.getInstance().fetchMember(roomId, member.getAccount(), new SimpleCallback<ChatRoomMember>() {
            @Override
            public void onResult(boolean success, ChatRoomMember result) {
                if (success) {
                    showLongClickMenu(result);
                } else {
                    Toast.makeText(getActivity(), R.string.chatroom_fetch_member_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 显示长按菜单
    private void showLongClickMenu(final ChatRoomMember currentMember) {
        // 不能操作的条件
        // 1、被操作用户是主播
        // 2、被操作者是自己
        // 3、用户自己是普通成员
        // 4、用户自己是受限用户
        // 5、用户自己是游客

        CustomAlertDialog alertDialog = new CustomAlertDialog(getActivity());

        // 不能操作的条件
        // 1、被操作用户是主播
        // 2、被操作者是自己
        if (currentMember.getMemberType() == MemberType.CREATOR
                || currentMember.getAccount().equals(DemoCache.getAccount())) {
            return;
        }

        if (currentMember.getMemberType() == MemberType.CREATOR
                || currentMember.getAccount().equals(DemoCache.getAccount())
                || ChatRoomMemberCache.getInstance().getChatRoomMember(roomId, DemoCache.getAccount()).getMemberType() == MemberType.NORMAL
                || ChatRoomMemberCache.getInstance().getChatRoomMember(roomId, DemoCache.getAccount()).getMemberType() == MemberType.LIMITED
                || ChatRoomMemberCache.getInstance().getChatRoomMember(roomId, DemoCache.getAccount()).getMemberType() == MemberType.GUEST) {

            if (getActivity() instanceof ChatRoomActivity) {
                //设置/取消屏蔽
                addBlockItem(currentMember, alertDialog);
                alertDialog.show();
            }

        } else {
            //踢出
            alertDialog.addItem(R.string.chatroom_kick_member, new CustomAlertDialog.onSeparateItemClickListener() {
                @Override
                public void onClick() {
                    kickMember(currentMember);
                }
            });
            // 设置/取消禁言
            addMutedItem(currentMember, alertDialog);
            // 设置/移出黑名单
            addBlackListItem(currentMember, alertDialog);
            // 设置/取消管理员
            addAdminItem(currentMember, alertDialog);
            // 设为/取消普通成员
            addNormalItem(currentMember, alertDialog);
            if (getActivity() instanceof ChatRoomActivity) {
                //设置/取消屏蔽
                addBlockItem(currentMember, alertDialog);
            }
            alertDialog.show();
        }

    }

    //检查是否在禁言名单里面
    private String CheckHave(List<UserBlockBean> mUserBlockBeans, String account) {
        for (UserBlockBean bean : mUserBlockBeans) {
            if (bean.getBlockUserId().equals(account)) {
                return bean.getId();
            }
        }
        return "";
    }

    // 添加屏蔽菜单
    boolean result = false;
    String blackID = "";

    private void addBlockItem(final ChatRoomMember chatRoomMember, CustomAlertDialog alertDialog) {
        List<UserBlockBean> mUserBlockBeans = ((ChatRoomActivity) getActivity()).mUserBlockBeans;
        if (mUserBlockBeans != null && mUserBlockBeans.size() > 0) {
            blackID = CheckHave(mUserBlockBeans, chatRoomMember.getAccount());
            result = blackID.length() > 0;
        }
        int titleId = result ? R.string.move_out_messageblack : R.string.chatroom_messageblack;
        alertDialog.addItem(titleId, new CustomAlertDialog.onSeparateItemClickListener() {
            @Override
            public void onClick() {
                setBlock(result, chatRoomMember);
            }
        });

    }


    // 添加禁言菜单
    private void addMutedItem(final ChatRoomMember chatRoomMember, CustomAlertDialog alertDialog) {
        int titleId = chatRoomMember.isMuted() ? R.string.cancel_muted : R.string.chatroom_muted;
        alertDialog.addItem(titleId, new CustomAlertDialog.onSeparateItemClickListener() {
            @Override
            public void onClick() {
                setMuted(chatRoomMember);
            }
        });
    }

    // 添加黑名单菜单
    private void addBlackListItem(final ChatRoomMember chatRoomMember, CustomAlertDialog alertDialog) {
        int titleId = chatRoomMember.isInBlackList() ? R.string.move_out_blacklist : R.string.chatroom_blacklist;
        alertDialog.addItem(titleId, new CustomAlertDialog.onSeparateItemClickListener() {
            @Override
            public void onClick() {
                setBlackList(chatRoomMember);
            }
        });
    }

    // 添加管理员菜单
    private void addAdminItem(final ChatRoomMember chatRoomMember, CustomAlertDialog alertDialog) {
        // 被操作者比操作者权限大, 则返回
        if (chatRoomMember.getMemberType() == MemberType.ADMIN
                && ChatRoomMemberCache.getInstance().getChatRoomMember(roomId, DemoCache.getAccount()).getMemberType() != MemberType.CREATOR) {
            return;
        }
        final boolean isAdmin = chatRoomMember.getMemberType() == MemberType.ADMIN;
        int titleId = isAdmin ? R.string.cancel_admin : R.string.chatroom_set_admin;
        alertDialog.addItem(titleId, new CustomAlertDialog.onSeparateItemClickListener() {
            @Override
            public void onClick() {
                setAdmin(chatRoomMember, isAdmin);
            }
        });
    }

    private void addNormalItem(final ChatRoomMember chatRoomMember, CustomAlertDialog alertDialog) {
        final boolean isNormal = chatRoomMember.getMemberType() == MemberType.NORMAL;
        int titleId = isNormal ? R.string.cancel_normal_member : R.string.set_normal_member;
        alertDialog.addItem(titleId, new CustomAlertDialog.onSeparateItemClickListener() {
            @Override
            public void onClick() {
                setNormalMember(chatRoomMember, isNormal);
            }
        });
    }

    // 踢人
    private void kickMember(final ChatRoomMember chatRoomMember) {
        Map<String, Object> reason = new HashMap<>();
        reason.put("reason", "就是不爽！");
        NIMClient.getService(ChatRoomService.class).kickMember(roomId, chatRoomMember.getAccount(), reason).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                Toast.makeText(getActivity(), R.string.chatroom_kick_member, Toast.LENGTH_SHORT).show();
                ChatRoomMember del = null;
                for (ChatRoomMember member : items) {
                    if (member.getAccount().equals(chatRoomMember.getAccount())) {
                        del = member;
                        break;
                    }
                }

                if (del != null) {
                    items.remove(del);
                    memberCache.remove(del.getAccount());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code) {
                Log.d(TAG, "kick member failed:" + code);
            }

            @Override
            public void onException(Throwable exception) {
                Log.d(TAG, "kick member exception:" + exception);
            }
        });
    }

    //添加屏蔽菜单
    private void setBlock(boolean result, final ChatRoomMember chatRoomMember) {
        String token = SharePrefUtil.getString(ct, "YangToken", "");
        if (result) {
            mNtfinaceApi.DeleteBlockID(blackID,token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Action1<JsonObject>() {
                                @Override
                                public void call(JsonObject mJsonObject) {
                                    if (mJsonObject.get("count").getAsInt() == 1) {
                                        ((ChatRoomActivity) getActivity()).getUserBlocklists();
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Toast.makeText(ct, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
        } else {
            String userId = SharePrefUtil.getString(ct, "userId", "");
            String blackID = chatRoomMember.getAccount();
            JsonObject mJsonObject = new JsonObject();
            mJsonObject.addProperty("avatar", chatRoomMember.getAvatar());
            mJsonObject.addProperty("name", chatRoomMember.getNick());
            mJsonObject.addProperty("roomId", chatRoomMember.getRoomId());


            mNtfinaceApi.putBlock(userId, blackID, mJsonObject,token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Action1<UserBlockBean>() {
                                @Override
                                public void call(UserBlockBean mJsonObject) {
                                    ((ChatRoomActivity) getActivity()).getUserBlocklists();
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Toast.makeText(ct, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
        }
    }

    // 设置/取消禁言
    private void setMuted(final ChatRoomMember chatRoomMember) {
        MemberOption option = new MemberOption(roomId, chatRoomMember.getAccount());
        NIMClient.getService(ChatRoomService.class).markChatRoomMutedList(!chatRoomMember.isMuted(), option)
                .setCallback(new RequestCallback<ChatRoomMember>() {
                    @Override
                    public void onSuccess(ChatRoomMember param) {
                        Toast.makeText(getActivity(), R.string.set_success, Toast.LENGTH_SHORT).show();
                        refreshList(param, chatRoomMember);
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.d(TAG, "set muted failed:" + code);
                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
    }

    // 设置/移出黑名单
    private void setBlackList(ChatRoomMember chatRoomMember) {
        MemberOption option = new MemberOption(roomId, chatRoomMember.getAccount());
        NIMClient.getService(ChatRoomService.class).markChatRoomBlackList(!chatRoomMember.isInBlackList(), option)
                .setCallback(new RequestCallback<ChatRoomMember>() {
                    @Override
                    public void onSuccess(ChatRoomMember param) {
                        Toast.makeText(getActivity(), R.string.set_success, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.d(TAG, "set black list failed:" + code);
                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
    }

    // 设置/取消管理员
    private void setAdmin(final ChatRoomMember member, boolean isAdmin) {
        NIMClient.getService(ChatRoomService.class)
                .markChatRoomManager(!isAdmin, new MemberOption(roomId, member.getAccount()))
                .setCallback(new RequestCallback<ChatRoomMember>() {
                    @Override
                    public void onSuccess(ChatRoomMember param) {
                        Toast.makeText(getActivity(), R.string.set_success, Toast.LENGTH_SHORT).show();
                        refreshList(param, member);
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.d(TAG, "set admin failed:" + code);
                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
    }

    // 设置/取消普通成员
    private void setNormalMember(final ChatRoomMember member, boolean isNormal) {
        NIMClient.getService(ChatRoomService.class).markNormalMember(!isNormal, new MemberOption(roomId, member.getAccount()))
                .setCallback(new RequestCallback<ChatRoomMember>() {
                    @Override
                    public void onSuccess(ChatRoomMember param) {
                        Toast.makeText(getActivity(), R.string.set_success, Toast.LENGTH_SHORT).show();
                        refreshList(param, member);
                    }

                    @Override
                    public void onFailed(int code) {

                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
    }

    private void refreshList(ChatRoomMember param, ChatRoomMember member) {
        ChatRoomMember temp = null;
        for (ChatRoomMember m : items) {
            if (m.getAccount().equals(param.getAccount())) {
                temp = m;
            }
        }
        member.setMemberType(param.getMemberType());
        items.remove(temp);
        items.add(member);
        Collections.sort(items, comp);
        adapter.notifyDataSetChanged();
    }

    private static Map<MemberType, Integer> compMap = new HashMap<>();

    static {
        compMap.put(MemberType.CREATOR, 0);
        compMap.put(MemberType.ADMIN, 1);
        compMap.put(MemberType.NORMAL, 2);
        compMap.put(MemberType.LIMITED, 3);
        compMap.put(MemberType.GUEST, 4);
    }

    private static Comparator<ChatRoomMember> comp = new Comparator<ChatRoomMember>() {
        @Override
        public int compare(ChatRoomMember lhs, ChatRoomMember rhs) {
            if (lhs == null) {
                return 1;
            }

            if (rhs == null) {
                return -1;
            }

            return compMap.get(lhs.getMemberType()) - compMap.get(rhs.getMemberType());
        }
    };
}
