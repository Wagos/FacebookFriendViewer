package com.example.facebookfriendviewer.utils;

import android.content.Context;
import com.facebook.Session;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class implement downloading images from facebook
 *
 * @author Witkowsky Dmitry
 */
public class FacebookImageDownloader extends BaseImageDownloader {
    public FacebookImageDownloader(Context context) {
        super(context);
    }

    @Override
    protected InputStream getStreamFromNetwork(String imageUri, Object extra) throws IOException {
        imageUri += "?access_token=" + Session.getActiveSession().getAccessToken();
        return super.getStreamFromNetwork(imageUri, extra);
    }
}
