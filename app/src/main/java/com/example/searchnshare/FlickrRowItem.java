package com.example.searchnshare;

import android.graphics.Bitmap;

/**
 * This class is in item that is used when adding a post to the flickr fragment.
 */
public class FlickrRowItem {

    private Bitmap imageBitmap;
    private String title;
    private String url;

    /**
     * This is the constructor of the class. It takes three parameters, all are used to correctly
     * set the views of a new row in the flickr fragment.
     *
     * @param title
     * @param imageBitmap
     * @param url
     */
    public FlickrRowItem(String title, Bitmap imageBitmap, String url) {
        this.imageBitmap = imageBitmap;
        this.title = title;
        this.url = url;
    }
    /**
     * Returns the Bitmap of this item.
     *
     * @return imageBitmap is a Bitmap.
     */
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    /**
     * Sets the imageBitmap of this item using the parameter Bitmap imageBitmap.
     */
    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    /**
     * Returns the title of this item.
     *
     * @return title is a String.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of this item using the parameter String title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the URL of this item.
     *
     * @return URL is a String.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url of this item to the parameter String URL.
     *
     * @param url is a String.
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
