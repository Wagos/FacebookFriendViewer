package com.example.facebookfriendviewer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.facebookfriendviewer.database.DbService;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Provides base for other activities
 *
 * @author Witkowsky Dmitry
 */
public abstract class BaseActivity extends Activity {

    protected ImageLoader imageLoader = ImageLoader.getInstance();     // Provides loading and caching images

    protected static DbService dbService;  //Provides work with database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbService = new DbService(this);
        dbService.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear_cache:
                imageLoader.clearMemoryCache();
                imageLoader.clearDiscCache();
                return true;
            case R.id.menu_item_drop_data:
                dbService.dropData();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbService.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbService.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbService.close();
    }

    public DbService getDbService() {
        return dbService;
    }
}
