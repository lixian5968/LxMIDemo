package com.zongbutech.ntfinance.session.viewholder;

import com.netease.nim.uikit.session.viewholder.MsgViewHolderText;
import com.zongbutech.ntfinance.session.extension.DefaultCustomAttachment;
import com.zongbutech.ntfinance.session.extension.Lx2CustomAttachment;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderDefCustom extends MsgViewHolderText {

    @Override
    protected String getDisplayText() {
        if(message.getAttachment() instanceof Lx2CustomAttachment){
            Lx2CustomAttachment attachment = (Lx2CustomAttachment) message.getAttachment();
//            attachment.parseData();
//            return  attachment.getContent();
            return  "哈哈哈";
        }else{
            DefaultCustomAttachment attachment = (DefaultCustomAttachment) message.getAttachment();
            return "type: " + attachment.getType() + ", data: " + attachment.getContent();
        }

    }
}
