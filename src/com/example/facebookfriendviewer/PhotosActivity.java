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
import android.widget.GridView;
import com.example.facebookfriendviewer.adapters.PhotoCursorAdapter;
import com.example.facebookfriendviewer.objects.Album;
import com.example.facebookfriendviewer.tasks.FacebookPhotosLoadTask;
import com.example.facebookfriendviewer.tasks.LoadCompleteListener;
import com.example.facebookfriendviewer.utils.Utils;

/**
 * Activity display photos from given album
 *
 * @author Witkowsky Dmitry
 */

public class PhotosActivity extends BaseActivity {

    private GridView gridView;
    private Button buttonRefresh;
    private long albumID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums);
        albumID = getIntent().getLongExtra("id", -1);
        init();

    }

    private void init() {

        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setNumColumns(2);
        Cursor cursor = getDbService().getPhotoCursor(Long.toString(albumID));

        if (Utils.cursorEmpty(cursor)) {
            onClickRefresh();
        }
        gridView.setAdapter(
                new PhotoCursorAdapter(this, cursor, R.layout.photo_view));
        buttonRefresh = (Button) findViewById(R.id.buttonRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRefresh();
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                onPhotoItemClick(id, position);
            }
        });
    }

    private void onPhotoItemClick(long id, int position) {
        Intent intent = new Intent(PhotosActivity.this,
                PhotoFlipActivity.class);

        intent.putExtra("albumId", albumID);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    private void onClickRefresh() {
        Album album = getDbService().getAlbum(albumID);
        FacebookPhotosLoadTask facebookAlbumsLoadTask = new FacebookPhotosLoadTask(
                this, album, new LoadCompleteListener() {
            @Override
            public void onLoadComplete() {
                refreshPhotos();
            }
        });
        facebookAlbumsLoadTask.execute();
    }

    /** change cursor in GridView adapter */
    private void refreshPhotos() {
        ((CursorAdapter) gridView.getAdapter()).changeCursor(
                getDbService().getPhotoCursor(Long.toString(albumID)));
    }
}
