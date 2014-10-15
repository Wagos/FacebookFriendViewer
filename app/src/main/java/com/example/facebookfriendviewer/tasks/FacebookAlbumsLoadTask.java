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

package com.example.facebookfriendviewer.tasks;

import com.example.facebookfriendviewer.BaseActivity;
import com.example.facebookfriendviewer.objects.Album;
import com.example.facebookfriendviewer.objects.Friend;
import com.example.facebookfriendviewer.utils.Utils;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;

import java.util.List;

/**
 * Class implement facebook album loading
 * If albums is exists they are stored in DB
 * Old albums are removed
 */
public class FacebookAlbumsLoadTask extends AbstractLoadTask {

    public static final String ALBUMS = "/albums";
    public static final String PICTURE = "/picture";

    private Friend friend;

    public FacebookAlbumsLoadTask(BaseActivity context, Friend friend,
            LoadCompleteListener callback) {
        super(context, callback);
        this.friend = friend;
    }

    @Override
    public Void call() {
        Response response = Request
                .newGraphPathRequest(Session.getActiveSession(),
                        friend.getAccID() + ALBUMS, null).executeAndWait();

        GraphObjectList<GraphObject> list = Utils.listGraphObjectFromResponse(response);

        if (list != null && list.size() > 0) {
            dbService.deleteFriendAlbums(friend.getId());
            Album album;
            for (GraphObject obj : list) {
                album = Utils.convertToAlbumObject(obj);
                album.setAccId(friend.getId());
                dbService.saveAlbum(album);
            }
        }

        return null;
    }

}
