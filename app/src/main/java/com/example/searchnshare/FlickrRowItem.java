package com.example.searchnshare;

import android.graphics.Bitmap;

public class FlickrRowItem {

    private Bitmap imageBitmap;
    private String title;
    private String url;

    public FlickrRowItem(String title, Bitmap imageBitmap, String url) {
        this.imageBitmap = imageBitmap;
        this.title = title;
        this.url = url;
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
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
