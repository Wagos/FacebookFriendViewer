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

package com.example.facebookfriendviewer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.facebookfriendviewer.R;
import com.example.facebookfriendviewer.database.TableConstants;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Adapter for displaying data from SQLite database on view (Text and Image)
 * Cursor must have fields: "Name", "Picture"
 * Adapter using ImageLoader class to async loading images
 *
 * @author Witkowsky Dmitry
 */

public class ViewCursorAdapter extends CursorAdapter {

    private ImageLoader loader = ImageLoader.getInstance();
    private final int viewLayout;

    /**
     * Constructor contains building options for ImageLoader
     *
     * @param context    application context
     * @param c          cursor for adapter
     * @param viewLayout Layout resource for displaying
     */
    public ViewCursorAdapter(Context context, Cursor c, int viewLayout) {
        super(context, c, true);
        this.viewLayout = viewLayout;
    }

    static class ViewHolder {
        public ImageView image;
        public TextView name;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewLayout, null, false);
        holder = new ViewHolder();
        holder.image = (ImageView) view.findViewById(R.id.picture);
        holder.name = (TextView) view.findViewById(R.id.name);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(cursor.getString(cursor.getColumnIndex(TableConstants.NAME)));
        loader.displayImage(cursor.getString(cursor.getColumnIndex(TableConstants.PICTURE)),
                holder.image);
    }

}
