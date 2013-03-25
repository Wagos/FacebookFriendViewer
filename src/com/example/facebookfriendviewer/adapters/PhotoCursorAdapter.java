package com.example.facebookfriendviewer.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import com.example.facebookfriendviewer.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
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
