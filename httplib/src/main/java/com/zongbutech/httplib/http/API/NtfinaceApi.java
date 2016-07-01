package com.zongbutech.httplib.http.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zongbutech.httplib.http.Bean.OrderBean;
import com.zongbutech.httplib.http.Bean.UserBlockBean;
import com.zongbutech.httplib.http.Bean.UserGlod;
import com.zongbutech.httplib.http.Bean.UserInfo;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lixian on 2016/6/23.
 */
public interface NtfinaceApi {

    public static String BaseApi = "http://webstore-api.dev.zongbutech.com/api/v1/";
    public static String BaseApiYang = "http://112.124.0.91/Api/v1/";
    //获取配置信息
    public static String getConfigs = BaseApi + "ntfinance/Configs";

    //获取配置信息
    @GET("ntfinance/Configs/findByCode/nim-config")
    Observable<JsonArray> getConfigs();


    //登陆
    @POST("users/login")
    Observable<JsonObject> getUserIdLogin(@Body JsonObject mJsonObject);

    //获取用户信息
    @GET("ntfinance/users/findOne")
    Observable<UserInfo> getUserInfo(@Query("filter[where][id]") String userID, @Query("access_token") String Token);

    //获取Mi登陆信息
    @POST("ntfinance/users/{userID}/NIMToken")
    Observable<JsonObject> getMiInfo(@Path("userID") String userID, @Query("access_token") String Token);


    //获取Chatrooms的房间数目
    public static String getChatrooms = BaseApi + "ntfinance/Chatrooms?filter[order]=updatedAt DESC&filter[limit]=1000&filter[offset]=0";


    //获取用户频闭人员
    @GET("ntfinance/UserBlocklists")
    Observable<JsonArray> getUserBlocklists(@Query("filter") String filter);


    //删除屏蔽
    @DELETE("ntfinance/UserBlocklists/{BlockID}")
    Observable<JsonObject> DeleteBlockID(@Path("BlockID") String BlockID,@Query("access_token") String access_token);

    //增加屏蔽
    @PUT("ntfinance/users/{UserID}/blockList/rel/{BlockUserID}")
    Observable<UserBlockBean> putBlock(@Path("UserID") String UserID, @Path("BlockUserID") String BlockUserID, @Body JsonObject mJsonObject,@Query("access_token") String access_token);


    //获取用户金币数目
    @GET("ntfinance/users/{UserID}/ntfUserInfo")
    Observable<UserGlod> getUserGlod(@Path("UserID") String UserID,@Query("filter[order]") String order,@Query("access_token") String access_token);

    //发送gold
    @POST("ntfinance/GamificationOrders")
    Observable<OrderBean> sendUserGlod(@Query("access_token") String access_token,@Body JsonObject mJsonObject);

}
