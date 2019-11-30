package com.example.searchnshare;

import android.graphics.Bitmap;

public class FlickrRowItem {

    private Bitmap imageBitmap;
    private String title;

    public FlickrRowItem(String title, Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
        this.title = title;
    }
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
