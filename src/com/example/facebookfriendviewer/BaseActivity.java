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

import android.view.Menu;
import android.view.MenuItem;
import com.example.facebookfriendviewer.database.DbService;
import com.google.inject.Inject;
import com.nostra13.universalimageloader.core.ImageLoader;
import roboguice.activity.RoboActivity;

/**
 * Provides base for other activities
 *
 * @author Witkowsky Dmitry
 */
public abstract class BaseActivity extends RoboActivity {

    protected ImageLoader imageLoader = ImageLoader
            .getInstance();     // Provides loading and caching images

    @Inject
    protected DbService dbService;  //Provides work with database

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
                refreshCursor();
                return true;
            default:
                return false;
        }
    }

    protected void refreshCursor(){}

    public DbService getDbService() {
        return dbService;
    }
}
