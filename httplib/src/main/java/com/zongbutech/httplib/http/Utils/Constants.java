package com.zongbutech.httplib.http.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sll on 2015/8/21.
 */
public class Constants {

    public static String Cookie = "";

    public static final int PAZE_SIZE = 1;

    public static final int PAZE_Roll = 10;

    public static final String BaseImageUrl = "http://dev-weixin.loveiparty.com/";

    public static String DateToString(Date mDate){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(mDate);
    }

    public static String OfficialID = "576b4ac2a957f19907c3b587";

}
