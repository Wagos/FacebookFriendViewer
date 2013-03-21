package com.example.facebookfriendviewer.tasks;

import com.example.facebookfriendviewer.BaseActivity;
import com.example.facebookfriendviewer.objects.Album;
import com.example.facebookfriendviewer.objects.Friend;
import com.example.facebookfriendviewer.utils.Utils;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;

import java.util.List;

/**
 * Class implement facebook album loading
 * If albums is exists they are stored in DB
 * Old albums are removed
 *
 * @author Witkowsky Dmitry
 */
public class FacebookAlbumsLoadTask extends AbstractLoadTask {

    public static final String ALBUMS = "/albums";
    public static final String PICTURE = "/picture";
    private Friend friend;

    public FacebookAlbumsLoadTask(BaseActivity context, Friend friend, LoadCompleteListener callback) {
        super(context, callback);
        this.friend = friend;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Response response = Request.newGraphPathRequest(Session.getActiveSession(), friend.getAccID() + ALBUMS, null).executeAndWait();
        List<GraphObject> list = Utils.listGraphObjectFromResponse(response);

        if (list != null && list.size() > 0) {
            context.getDbService().deleteFriendAlbums(friend.getId());
            Album album;
            for (GraphObject obj : list) {
                album = Utils.convertToAlbumObject(obj);
                album.setAccId(friend.getId());
                context.getDbService().saveAlbum(album);
            }
        }

        return null;
    }

}
