/*******************************************************************************
 * Copyright 2013 Witkowsky Dmitry
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

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

    /** Creating tables */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FRIEND_TABLE);
        db.execSQL(CREATE_ALBUM_TABLE);
        db.execSQL(CREATE_PHOTO_TABLE);
    }

    /** Recreating tables */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_ALBUM_TABLE);
        db.execSQL(DROP_FRIEND_TABLE);
        db.execSQL(DROP_PHOTO_TABLE);
        onCreate(db);
    }
}
