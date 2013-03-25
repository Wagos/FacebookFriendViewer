package com.example.facebookfriendviewer.tasks;

import com.example.facebookfriendviewer.BaseActivity;
import com.example.facebookfriendviewer.objects.Album;
import com.example.facebookfriendviewer.objects.Photo;
import com.example.facebookfriendviewer.utils.Utils;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;

import java.util.List;

/**
 * @author Witkowsky Dmitry
 */
public class FacebookPhotosLoadTask extends AbstractLoadTask{
    private static final String PHOTOS = "/photos";
    private Album album;
    public FacebookPhotosLoadTask(BaseActivity photosActivity, Album album, LoadCompleteListener loadCompleteListener) {
        super(photosActivity, loadCompleteListener);
        this.album=album;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Response response = Request.newGraphPathRequest(Session.getActiveSession(), album.getAlbumId() + PHOTOS, null).executeAndWait();
        List<GraphObject> list = Utils.listGraphObjectFromResponse(response);

        if (list != null && list.size() > 0) {
            context.getDbService().deletePhotos(album.getId());
            Photo photo;
            for (GraphObject obj : list) {
                photo = Utils.convertToPhoto(obj);
                photo.setAlbumId(album.getId());
                context.getDbService().savePhoto(photo);
            }
        }
        return null;
    }
}
