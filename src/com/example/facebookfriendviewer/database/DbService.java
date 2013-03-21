package com.example.facebookfriendviewer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.facebookfriendviewer.objects.Album;
import com.example.facebookfriendviewer.objects.Friend;

import static com.example.facebookfriendviewer.database.TableConstants.*;

/**
 * Class for managing DB data
 *
 * @author Witkowsky Dmitry
 */
public class DbService {

    private DBHelper helper;
    private SQLiteDatabase database;

    public DbService(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * Save Friend object to DB
     *
     * @param friend object to save
     */
    public void saveFriend(Friend friend) {
        database.insert(FRIENDS_TABLE_NAME,
                null,
                getFriendContentValues(friend));
    }

    /**
     * Convert Friend object to Content values
     *
     * @param friend object to convert
     * @return Content values to store
     */
    private ContentValues getFriendContentValues(Friend friend) {
        ContentValues values = new ContentValues();
        values.put(ID, friend.getAccID());
        values.put(NAME, friend.getName());
        values.put(PICTURE, friend.getProfileImageUrl());
        return values;
    }

    /**
     * Requests friends from the database
     *
     * @return Cursor with friends
     */
    public Cursor getFriendList() {
        return database.query(FRIENDS_TABLE_NAME
                , null, null, null, null, null,
                NAME);
    }

    /**
     * Requests friend from the database
     *
     * @param id friend id
     * @return Friend object
     */
    public Friend getFriend(long id) {

        Cursor cursor = database.query(FRIENDS_TABLE_NAME, null,
                "_id = ?", new String[]{Long.toString(id)},
                null, null, null);

        if (!cursor.moveToFirst()) return null;
        return friendFromCursor(cursor);
    }

    private Friend friendFromCursor(Cursor cursor) {
        Friend friend = new Friend();
        friend.setId(cursor.getString(0));
        friend.setAccID(cursor.getString(1));
        friend.setName(cursor.getString(2));
        friend.setProfileImageUrl(cursor.getString(3));
        return friend;
    }

    /**
     * Save Album object to DB
     *
     * @param album object to save
     */
    public void saveAlbum(Album album) {
        database.insert(ALBUM_TABLE_NAME, null, getAlbumContentValues(album));
    }

    private ContentValues getAlbumContentValues(Album album) {
        ContentValues values = new ContentValues();
        values.put(ID, album.getAlbumId());
        values.put(NAME, album.getName());
        values.put(PICTURE, album.getPictureUrl());
        values.put(ACC_ID, album.getAccId());
        return values;
    }

    /**
     * @param accId Account id to request
     * @return Cursor with Albums of specific account
     */

    public Cursor getAlbumCursor(String accId) {
        return database.query(ALBUM_TABLE_NAME
                , null,
                ACC_ID + " = " + accId,
                null, null, null,
                NAME);
    }

    /**
     * Delete all friends from DB
     */
    public void deleteFriends() {
        database.delete(FRIENDS_TABLE_NAME, null, null);
    }

    /**
     * Delete all albums of specific account
     */
    public void deleteFriendAlbums(String accID) {
        database.delete(ALBUM_TABLE_NAME,
                ACC_ID + " = " + accID,
                null);
    }

    /**
     * Recreate data tables
     */
    public void dropData() {
        helper.onUpgrade(database, 0, 0);
    }

    /**
     * Open DB for writing
     */
    public void open() {
        database = helper.getWritableDatabase();
    }

    /**
     * Close access to DB
     */
    public void close() {
        helper.close();
    }
}
