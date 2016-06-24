package com.example;
import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static final int VERSION = 1;
    public static final String GREEN_DAO_CODE_PATH = "../Android_ntfinace/httplib/src/main/java";

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(VERSION, "com.zongbutech.httplib.http.db");

        Entity ChatRoomBean = schema.addEntity("ChatRoomBean");
        ChatRoomBean.setSuperclass("com.zongbutech.httplib.http.Bean.BaseBean");
        ChatRoomBean.addIdProperty();
        ChatRoomBean.addDateProperty("createdAt");
        ChatRoomBean.addStringProperty("creatorId");
        ChatRoomBean.addStringProperty("iconURL");
        ChatRoomBean.addIntProperty("maxUser");
        ChatRoomBean.addStringProperty("name");
        ChatRoomBean.addStringProperty("nimRoomId");
        ChatRoomBean.addStringProperty("ownerId");
        ChatRoomBean.addIntProperty("priority");
        ChatRoomBean.addIntProperty("status");
        ChatRoomBean.addDateProperty("updatedAt");

        File f = new File(GREEN_DAO_CODE_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }

        new DaoGenerator().generateAll(schema, f.getAbsolutePath());
    }
}
