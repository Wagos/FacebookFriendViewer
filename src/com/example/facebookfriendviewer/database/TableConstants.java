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

/**
 * Interface contains constants for DB managing
 * Here field names for table and queries to create and drop tables
 *
 * @author Witkowsky Dmitry
 */
public class TableConstants {

    public static final String PHOTO_TABLE_NAME = "photos";
    public static final String ALBUM_ID = "albumID";

    private TableConstants() {
    }

    public static final String CREATE_TABLE = "CREATE TABLE ";

    public static final String GEN_ID = " (_id integer primary key autoincrement, ";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    public static final String FRIENDS_TABLE_NAME = "friends";

    public static final String ID = "objectID";
    public static final String NAME = "Name";
    public static final String PICTURE = "pictureUrl";

    public static final String CREATE_FRIEND_TABLE =
            CREATE_TABLE + FRIENDS_TABLE_NAME
                    + GEN_ID
                    + ID + " TEXT, "
                    + NAME + " TEXT, "
                    + PICTURE + " TEXT)";

    public static final String DROP_FRIEND_TABLE =
            DROP_TABLE + FRIENDS_TABLE_NAME;

    public static final String ALBUM_TABLE_NAME = "albums";

    public static final String ACC_ID = "accID";

    public static final String CREATE_ALBUM_TABLE =
            CREATE_TABLE + ALBUM_TABLE_NAME
                    + GEN_ID
                    + ID + " TEXT,"
                    + NAME + " TEXT, "
                    + PICTURE + " TEXT, "
                    + ACC_ID + " INTEGER," +
                    " FOREIGN KEY ( " + ACC_ID + " ) REFERENCES " +
                    FRIENDS_TABLE_NAME + "(_id)" + ")";

    public static final String DROP_ALBUM_TABLE = DROP_TABLE + ALBUM_TABLE_NAME;

    public static final String CREATE_PHOTO_TABLE =
            CREATE_TABLE + PHOTO_TABLE_NAME
                    + GEN_ID
                    + ID + " TEXT,"
                    + PICTURE + " TEXT, "
                    + ALBUM_ID + " INTEGER,"
                    + " FOREIGN KEY ( " + ALBUM_ID + " ) REFERENCES " +
                    ALBUM_TABLE_NAME + "(_id)" + ")";
    public static final String DROP_PHOTO_TABLE = DROP_TABLE + PHOTO_TABLE_NAME;

    public static final String DELETE_FROM ="DELETE FROM ";
}
