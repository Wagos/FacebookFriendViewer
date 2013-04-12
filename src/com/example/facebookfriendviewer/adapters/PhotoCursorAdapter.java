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
import com.example.facebookfriendviewer.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import static com.example.facebookfriendviewer.database.TableConstants.PICTURE;

/**
 * @author Witkowsky Dmitry
 */
public class PhotoCursorAdapter extends CursorAdapter {

    private ImageLoader loader = ImageLoader.getInstance();

    public PhotoCursorAdapter(Context context, Cursor c) {
        super(context, c, true);
    }
    static class ViewHolder {
        public ImageView image;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.photo_view, null, false);
        holder = new ViewHolder();
        holder.image = (ImageView) view.findViewById(R.id.picture);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        loader.displayImage(cursor.getString(cursor.getColumnIndex(PICTURE)),
                holder.image);
    }
}
