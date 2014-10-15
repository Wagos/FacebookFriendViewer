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

package com.example.facebookfriendviewer.utils;

import android.database.Cursor;
import com.example.facebookfriendviewer.objects.Album;
import com.example.facebookfriendviewer.objects.Friend;
import com.example.facebookfriendviewer.objects.Photo;
import com.facebook.Response;
import com.facebook.model.GraphMultiResult;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Provides facebook object convert operations
 * and other operation
 *
 * @author Witkowsky Dmitry
 */
public class Utils {

    private static final int BUFFER_SIZE = 1024;

    private static final String FACEBOOK_URL_FORMAT = "https://graph.facebook.com/%s/";
    private static final String PICTURE_URL_FORMAT =
            FACEBOOK_URL_FORMAT + "picture";
    public static final String ID = "id";
    public static final String NAME = "name";

    private Utils() {
    }

    public static void copyStream(InputStream is, OutputStream os) {
        try {
            byte[] bytes = new byte[BUFFER_SIZE];
            for (; ; ) {
                int count = is.read(bytes, 0, BUFFER_SIZE);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static GraphObjectList<GraphObject> listGraphObjectFromResponse(
            Response response) {
        GraphMultiResult multiResult = response
                .getGraphObjectAs(GraphMultiResult.class);

        if (multiResult == null) {
            return null;
        }

        return multiResult.getData();
    }

    public static Friend convertToFriendObject(GraphObject graphObject) {
        Friend friend = new Friend();
        JSONObject jsonObject = graphObject.getInnerJSONObject();
        String id = jsonObject.optString(ID);

        friend.setAccID(id);
        friend.setName(jsonObject.optString(NAME));
        JSONObject picture = jsonObject.optJSONObject("picture");
        if(picture != null){
            JSONObject data = picture.optJSONObject("data");
            if(data != null) {
                friend.setProfileImageUrl(data.optString("url"));
            }
        }

        return friend;
    }

    public static Album convertToAlbumObject(GraphObject graphObject) {
        Album album = new Album();
        String id = (String) graphObject.getProperty(ID);

        album.setAlbumId(id);
        album.setName((String) graphObject.getProperty(NAME));
        album.setPictureUrl(getFacebookPictureUrl(id));

        return album;
    }

    public static String getFacebookPictureUrl(String id) {
        return String.format(PICTURE_URL_FORMAT, id);
    }

    public static boolean cursorEmpty(Cursor cursor) {
        return cursor.getCount() < 1;
    }

    public static Photo convertToPhoto(GraphObject graphObject) {
        Photo photo = new Photo();
        String id = (String) graphObject.getProperty(ID);

        photo.setPhotoId(id);
        photo.setPictureUrl(getFacebookPictureUrl(id));
        return photo;
    }
}
