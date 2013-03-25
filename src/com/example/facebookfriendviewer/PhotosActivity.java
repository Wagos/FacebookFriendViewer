package com.example.facebookfriendviewer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import com.example.facebookfriendviewer.adapters.PhotoCursorAdapter;
import com.example.facebookfriendviewer.objects.Album;
import com.example.facebookfriendviewer.tasks.FacebookPhotosLoadTask;
import com.example.facebookfriendviewer.tasks.LoadCompleteListener;
import com.example.facebookfriendviewer.utils.Utils;

/**
 * Activity display photos from given album
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
        gridView.setAdapter(new PhotoCursorAdapter(this, cursor));

        buttonRefresh = (Button) findViewById(R.id.buttonRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRefresh();
            }
        });
    }

    private void onClickRefresh() {
        Album album = getDbService().getAlbum(albumID);
        FacebookPhotosLoadTask facebookAlbumsLoadTask = new FacebookPhotosLoadTask(this,album, new LoadCompleteListener() {
            @Override
            public void onLoadComplete() {
                refreshPhotos();
            }
        });
        facebookAlbumsLoadTask.execute();
    }

    /**
     * change cursor in GridView adapter
     */
    private void refreshPhotos() {
        ((CursorAdapter) gridView.getAdapter()).changeCursor(getDbService().getPhotoCursor(Long.toString(albumID)));
    }
}
