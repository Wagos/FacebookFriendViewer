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

import android.database.sqlite.SQLiteDatabase;
import com.example.facebookfriendviewer.database.DBHelper;
import com.google.inject.AbstractModule;

/**
 * RoboGuice module for configuration binding dependencies
 */
public class FriendViewerModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(SQLiteDatabase.class).toProvider(DBHelper.class);
    }
}
