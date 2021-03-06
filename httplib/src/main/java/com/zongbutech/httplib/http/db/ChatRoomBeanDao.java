package com.zongbutech.httplib.http.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.zongbutech.httplib.http.db.ChatRoomBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHAT_ROOM_BEAN".
*/
public class ChatRoomBeanDao extends AbstractDao<ChatRoomBean, Long> {

    public static final String TABLENAME = "CHAT_ROOM_BEAN";

    /**
     * Properties of entity ChatRoomBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CreatedAt = new Property(1, java.util.Date.class, "createdAt", false, "CREATED_AT");
        public final static Property ObjectId = new Property(2, String.class, "objectId", false, "OBJECT_ID");
        public final static Property CreatorId = new Property(3, String.class, "creatorId", false, "CREATOR_ID");
        public final static Property IconURL = new Property(4, String.class, "iconURL", false, "ICON_URL");
        public final static Property MaxUser = new Property(5, Integer.class, "maxUser", false, "MAX_USER");
        public final static Property Name = new Property(6, String.class, "name", false, "NAME");
        public final static Property NimRoomId = new Property(7, String.class, "nimRoomId", false, "NIM_ROOM_ID");
        public final static Property OwnerId = new Property(8, String.class, "ownerId", false, "OWNER_ID");
        public final static Property Priority = new Property(9, Integer.class, "priority", false, "PRIORITY");
        public final static Property Status = new Property(10, Integer.class, "status", false, "STATUS");
        public final static Property HasPassword = new Property(11, Boolean.class, "hasPassword", false, "HAS_PASSWORD");
        public final static Property UpdatedAt = new Property(12, java.util.Date.class, "updatedAt", false, "UPDATED_AT");
    };


    public ChatRoomBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ChatRoomBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHAT_ROOM_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"CREATED_AT\" INTEGER," + // 1: createdAt
                "\"OBJECT_ID\" TEXT," + // 2: objectId
                "\"CREATOR_ID\" TEXT," + // 3: creatorId
                "\"ICON_URL\" TEXT," + // 4: iconURL
                "\"MAX_USER\" INTEGER," + // 5: maxUser
                "\"NAME\" TEXT," + // 6: name
                "\"NIM_ROOM_ID\" TEXT," + // 7: nimRoomId
                "\"OWNER_ID\" TEXT," + // 8: ownerId
                "\"PRIORITY\" INTEGER," + // 9: priority
                "\"STATUS\" INTEGER," + // 10: status
                "\"HAS_PASSWORD\" INTEGER," + // 11: hasPassword
                "\"UPDATED_AT\" INTEGER);"); // 12: updatedAt
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHAT_ROOM_BEAN\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ChatRoomBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        java.util.Date createdAt = entity.getCreatedAt();
        if (createdAt != null) {
            stmt.bindLong(2, createdAt.getTime());
        }
 
        String objectId = entity.getObjectId();
        if (objectId != null) {
            stmt.bindString(3, objectId);
        }
 
        String creatorId = entity.getCreatorId();
        if (creatorId != null) {
            stmt.bindString(4, creatorId);
        }
 
        String iconURL = entity.getIconURL();
        if (iconURL != null) {
            stmt.bindString(5, iconURL);
        }
 
        Integer maxUser = entity.getMaxUser();
        if (maxUser != null) {
            stmt.bindLong(6, maxUser);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(7, name);
        }
 
        String nimRoomId = entity.getNimRoomId();
        if (nimRoomId != null) {
            stmt.bindString(8, nimRoomId);
        }
 
        String ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindString(9, ownerId);
        }
 
        Integer priority = entity.getPriority();
        if (priority != null) {
            stmt.bindLong(10, priority);
        }
 
        Integer status = entity.getStatus();
        if (status != null) {
            stmt.bindLong(11, status);
        }
 
        Boolean hasPassword = entity.getHasPassword();
        if (hasPassword != null) {
            stmt.bindLong(12, hasPassword ? 1L: 0L);
        }
 
        java.util.Date updatedAt = entity.getUpdatedAt();
        if (updatedAt != null) {
            stmt.bindLong(13, updatedAt.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ChatRoomBean readEntity(Cursor cursor, int offset) {
        ChatRoomBean entity = new ChatRoomBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : new java.util.Date(cursor.getLong(offset + 1)), // createdAt
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // objectId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // creatorId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // iconURL
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // maxUser
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // name
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // nimRoomId
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // ownerId
            cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // priority
            cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10), // status
            cursor.isNull(offset + 11) ? null : cursor.getShort(offset + 11) != 0, // hasPassword
            cursor.isNull(offset + 12) ? null : new java.util.Date(cursor.getLong(offset + 12)) // updatedAt
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ChatRoomBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCreatedAt(cursor.isNull(offset + 1) ? null : new java.util.Date(cursor.getLong(offset + 1)));
        entity.setObjectId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreatorId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIconURL(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMaxUser(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setNimRoomId(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setOwnerId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPriority(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setStatus(cursor.isNull(offset + 10) ? null : cursor.getInt(offset + 10));
        entity.setHasPassword(cursor.isNull(offset + 11) ? null : cursor.getShort(offset + 11) != 0);
        entity.setUpdatedAt(cursor.isNull(offset + 12) ? null : new java.util.Date(cursor.getLong(offset + 12)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ChatRoomBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ChatRoomBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
