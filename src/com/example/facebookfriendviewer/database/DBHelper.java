package com.example.facebookfriendviewer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.facebookfriendviewer.database.TableConstants.*;

/**
 * Class for managing DB
 *
 * @author Witkowsky Dmitry
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String FACEBOOK_FRIEND_DB = "FriendViewerDB";
    private static final int DB_VERSION = 6;



    public DBHelper(Context context) {
        super(context, FACEBOOK_FRIEND_DB, null, DB_VERSION);
    }

    /**
     * Creating tables
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FRIEND_TABLE);
        db.execSQL(CREATE_ALBUM_TABLE);
        db.execSQL(CREATE_PHOTO_TABLE);
    }

    /**
     * Recreating tables
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_ALBUM_TABLE);
        db.execSQL(DROP_FRIEND_TABLE);
        db.execSQL(DROP_PHOTO_TABLE);
        onCreate(db);
    }
}
