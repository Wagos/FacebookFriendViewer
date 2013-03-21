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

    public FacebookFriendsLoadTask(BaseActivity context, LoadCompleteListener callback) {
        super(context, callback);
    }

    @Override
    protected Void doInBackground(Void... params) {
        Response response = Request.newMyFriendsRequest(Session.getActiveSession(), null).executeAndWait();
        List<GraphObject> list = Utils.listGraphObjectFromResponse(response);

        if (list != null && list.size() > 0) {
            context.getDbService().deleteFriends();
            for (GraphObject obj : list) {
                context.getDbService().saveFriend(Utils.convertToFriendObject(obj));
            }
        }
        return null;
    }


}
