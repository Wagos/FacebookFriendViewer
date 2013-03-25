package com.example.facebookfriendviewer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import com.example.facebookfriendviewer.adapters.ViewCursorAdapter;
import com.example.facebookfriendviewer.objects.Friend;
import com.example.facebookfriendviewer.tasks.FacebookAlbumsLoadTask;
import com.example.facebookfriendviewer.tasks.LoadCompleteListener;
import com.example.facebookfriendviewer.utils.Utils;

/**
 * Activity to displaying users albums
 */
public class AlbumsActivity extends BaseActivity {

    private GridView gridView;
    private Button buttonRefresh;
    private long accID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums);
        accID = getIntent().getLongExtra("id", -1);
        init();

    }

    private void init() {

        gridView = (GridView) findViewById(R.id.grid_view);

        Cursor cursor = getDbService().getAlbumCursor(Long.toString(accID));

        if (Utils.cursorEmpty(cursor)) {
            onClickRefresh();
        }
        gridView.setAdapter(new ViewCursorAdapter(this, cursor, R.layout.album_view));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onAlbumItemClick(id);
            }
        });
        buttonRefresh = (Button) findViewById(R.id.buttonRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRefresh();
            }
        });
    }

    private void onAlbumItemClick(long id) {
        Intent intent = new Intent(AlbumsActivity.this, PhotosActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    private void onClickRefresh() {
        Friend friend = getDbService().getFriend(accID);
        FacebookAlbumsLoadTask facebookAlbumsLoadTask = new FacebookAlbumsLoadTask(this, friend, new LoadCompleteListener() {
            @Override
            public void onLoadComplete() {
                refreshAlbums();
            }
        });
        facebookAlbumsLoadTask.execute();
    }

    /**
     * change cursor in GridView adapter
     */
    private void refreshAlbums() {
        ((CursorAdapter) gridView.getAdapter()).changeCursor(getDbService().getAlbumCursor(Long.toString(accID)));
    }
}
