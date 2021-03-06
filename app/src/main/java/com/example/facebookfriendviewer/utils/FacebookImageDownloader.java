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
    protected InputStream getStreamFromNetwork(String imageUri,
            Object extra) throws IOException {
        imageUri +=
                "?access_token=" + Session.getActiveSession().getAccessToken();
        return super.getStreamFromNetwork(imageUri, extra);
    }
}
