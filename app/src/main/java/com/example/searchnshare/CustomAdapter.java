package com.example.searchnshare;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * This class extends ArrayAdapter and is used to populate the listView inside of the FlickrFragment.
 */
public class CustomAdapter extends ArrayAdapter<FlickrRowItem> {

    Context context;

    public CustomAdapter(Context context, int resourceId,
                         List<FlickrRowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtUrl;
    }

    /**
     * This method gets the current row and fills the views with the correct information from the
     * current FlickrRowItem.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        FlickrRowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.flickr_row, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.flickr_row_title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.flickr_row_image);
            holder.txtUrl = (TextView) convertView.findViewById(R.id.flickr_row_url);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageBitmap(rowItem.getImageBitmap());
        holder.txtUrl.setText(rowItem.getUrl());

        return convertView;
    }
}
