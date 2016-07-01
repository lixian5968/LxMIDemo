package com.zongbutech.ntfinance.LxAdd;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.zongbutech.ntfinance.R;

/**
 * Created by lixian on 2016/6/30.
 */
public class SendVPopupWindow extends PopupWindow {

    private Context mContext;

    private View view;

    private Button send_it, call_it, guanzhu, btn_cancel;

    HeadImageView mHeadImageView;
    TextView mTextViewName;

    public SendVPopupWindow(Context mContext, View.OnClickListener itemsOnClick) {

        this.view = LayoutInflater.from(mContext).inflate(R.layout.send_v_pop, null);


        mHeadImageView = (HeadImageView) view.findViewById(R.id.mHeadImageView);
        mTextViewName = (TextView) view.findViewById(R.id.mTextViewName);

        send_it = (Button) view.findViewById(R.id.send_it);
        call_it = (Button) view.findViewById(R.id.call_it);
        guanzhu = (Button) view.findViewById(R.id.guanzhu);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        // 取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });
        // 设置按钮监听
        send_it.setOnClickListener(itemsOnClick);
        call_it.setOnClickListener(itemsOnClick);
        guanzhu.setOnClickListener(itemsOnClick);

        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
//        this.setAnimationStyle(R.style.sendv);

    }

    public void setNameAndIcon(String nick, String account) {
        if (mHeadImageView != null) {
            mHeadImageView.loadBuddyAvatar(account);
        }
        if (mTextViewName != null) {
            mTextViewName.setText(nick);
        }
    }
}
