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

package com.example.facebookfriendviewer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.example.facebookfriendviewer.adapters.ViewCursorAdapter;
import com.example.facebookfriendviewer.tasks.FacebookFriendsLoadTask;
import com.example.facebookfriendviewer.tasks.LoadCompleteListener;
import com.example.facebookfriendviewer.utils.FacebookImageDownloader;
import com.example.facebookfriendviewer.utils.Utils;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import java.util.Arrays;

/**
 * Root Activity
 * Allows view friends and login
 *
 * @author Witkowsky Dmitry
 */
public class MyActivity extends BaseActivity {

    private Button buttonRefresh;
    private LoginButton authButton;
    private ListView listView;

    private static final String[] FACEBOOK_PERMISSIONS = new String[]{"user_likes", "user_photos", "user_videos", "friends_likes", "friends_photos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initButtons();
        initImageLoader();
        initViews();
    }

    /**
     * Configure ImageLoader
     */
    private void initImageLoader() {
        imageLoader.init(new ImageLoaderConfiguration.Builder(this)
                .imageDownloader(new FacebookImageDownloader(this))       //Set downloader
                .defaultDisplayImageOptions(new DisplayImageOptions.Builder()
                        .showStubImage(R.drawable.com_facebook_profile_default_icon)
                        .showImageForEmptyUri(R.drawable.com_facebook_profile_default_icon)
                        .showImageOnFail(R.drawable.com_facebook_profile_default_icon)
                        .cacheInMemory()                                                  //set cache options
                        .cacheOnDisc()
                        .build())
                .build());
    }

    private void initButtons() {

        buttonRefresh = (Button) findViewById(R.id.buttonRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRefresh();
            }
        });

        authButton = (LoginButton) findViewById(R.id.authButton);
        authButton.setReadPermissions(Arrays.asList(FACEBOOK_PERMISSIONS));
        authButton.setSessionStatusCallback(new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (state == SessionState.OPENED)
                    controlsEnabling(true);
                else controlsEnabling(false);
            }
        });

        controlsEnabling(checkSession());
    }

    private boolean checkSession() {
        return Session.getActiveSession() != null && Session.getActiveSession().isOpened();
    }

    private void initViews() {

        listView = (ListView) findViewById(R.id.friendList);

        Cursor cursor = getDbService().getFriendList();
        if (Utils.cursorEmpty(cursor)) {
            onClickRefresh();
        }
        listView.setAdapter(new ViewCursorAdapter(this, cursor, R.layout.friend_view));

        listView.setOnScrollListener(new PauseOnScrollListener(imageLoader, false, true));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(id);
            }
        });
    }

    private void onListItemClick(long id) {
        Intent intent = new Intent(MyActivity.this, AlbumsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void controlsEnabling(boolean enable) {
        buttonRefresh.setEnabled(enable);
    }


    private void onClickRefresh() {

        FacebookFriendsLoadTask friendsTask = new FacebookFriendsLoadTask(this, new LoadCompleteListener() {
            @Override
            public void onLoadComplete() {
                refreshFriends();
            }
        });
        friendsTask.execute();
    }

    /**
     * change cursor in ListView adapter
     */
    private void refreshFriends() {
        ((CursorAdapter) listView.getAdapter()).changeCursor(dbService.getFriendList());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        controlsEnabling(checkSession());
    }


}
