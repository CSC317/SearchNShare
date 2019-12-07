package com.example.searchnshare;

import android.app.Activity;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MemeAdapter extends ArrayAdapter<MemeRowItem> {

    Context context;

    public MemeAdapter(Context context, int resourceId,
                         List<MemeRowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        MemeRowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.meme_row, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.meme_row_title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.meme_row_image);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageBitmap(rowItem.getImageBitmap());

        return convertView;
    }
}
