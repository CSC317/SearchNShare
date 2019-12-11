package com.example.searchnshare;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FavoritesAdapter extends ArrayAdapter<FavoriteListItem> {

    Context context;

    public FavoritesAdapter(Context context, int resourceId, List<FavoriteListItem> items) {
        super(context, resourceId, items);
        this.context = context;

    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView urlView;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        FavoriteListItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.favorite_row, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.favorite_row_title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.favorite_row_image);
            holder.urlView = (TextView) convertView.findViewById(R.id.favorite_row_url);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTitle.setText(rowItem.getTitle());
        if (rowItem.getResource().contentEquals("Reddit") || rowItem.getImageBitmap() == null) {
            Drawable myImage = context.getResources().getDrawable(R.drawable.reddit);
            holder.imageView.setImageDrawable(myImage);
        }
        else {
            holder.imageView.setImageBitmap(rowItem.getImageBitmap());
        }

        holder.urlView.setText(rowItem.getURL());

        return convertView;
    }
}