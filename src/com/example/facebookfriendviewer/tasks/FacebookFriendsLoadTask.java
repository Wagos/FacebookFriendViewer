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
import com.example.facebookfriendviewer.utils.Utils;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;

import java.util.List;

/**
 * Class implement facebook friends loading
 * If friends is exists they are stored in DB
 *
 * @author Witkowsky Dmitry
 */
public class FacebookFriendsLoadTask extends AbstractLoadTask {

    public FacebookFriendsLoadTask(BaseActivity context,
            LoadCompleteListener callback) {
        super(context, callback);
    }

    @Override
    protected Void doInBackground(Void... params) {
        Response response = Request
                .newMyFriendsRequest(Session.getActiveSession(), null)
                .executeAndWait();
        List<GraphObject> list = Utils.listGraphObjectFromResponse(response);

        if (list != null && list.size() > 0) {
            context.getDbService().deleteFriends();
            for (GraphObject obj : list) {
                context.getDbService()
                        .saveFriend(Utils.convertToFriendObject(obj));
            }
        }
        return null;
    }


}
