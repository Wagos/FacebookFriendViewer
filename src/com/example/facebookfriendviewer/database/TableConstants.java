package com.example.facebookfriendviewer.database;

/**
 * Interface contains constants for DB managing
 * Here field names for table and queries to create and drop tables
 *
 * @author Witkowsky Dmitry
 */
public class TableConstants {

    private TableConstants() {
    }

    public static final String CREATE_TABLE = "CREATE TABLE ";

    public static final String GEN_ID = " (_id integer primary key autoincrement, ";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

    public static final String FRIENDS_TABLE_NAME = "friends";

    public static final String ID = "objectID";
    public static final String NAME = "Name";
    public static final String PICTURE = "pictureUrl";

    public static final String CREATE_FRIEND_TABLE = CREATE_TABLE + FRIENDS_TABLE_NAME
            + GEN_ID
            + ID + " TEXT, "
            + NAME + " TEXT, "
            + PICTURE + " TEXT)";

    public static final String DROP_FRIEND_TABLE = DROP_TABLE + FRIENDS_TABLE_NAME;

    public static final String ALBUM_TABLE_NAME = "albums";

    public static final String ACC_ID = "accID";

    public static final String CREATE_ALBUM_TABLE = CREATE_TABLE + ALBUM_TABLE_NAME
            + GEN_ID
            + ID + " TEXT,"
            + NAME + " TEXT, "
            + PICTURE + " TEXT, "
            + ACC_ID + " INTEGER," +
            " FOREIGN KEY ( " + ACC_ID + " ) REFERENCES " + FRIENDS_TABLE_NAME + "(_id)" + ")";

    public static final String DROP_ALBUM_TABLE = DROP_TABLE + ALBUM_TABLE_NAME;
}
