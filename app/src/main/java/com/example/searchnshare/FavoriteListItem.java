package com.example.searchnshare;

import android.graphics.Bitmap;

/**
 * This class is in item that is used whe adding a post to the favorites fragment.
 */
public class FavoriteListItem {

    private Bitmap imageBitmap;
    private String title;
    //private String descrpition;
    private String resource;
    private String URL;

    /**
     * This is the constructor of the class. It takes three parameters, all are used to correctly
     * set the views of a new row in the favoites fragment.
     *
     * @param title
     * @param imageBitmap
     * @param resource
     */
    public FavoriteListItem(String title, Bitmap imageBitmap, String resource ) {
        this.imageBitmap = imageBitmap;
        this.title = title;
        //this.descrpition = descrpition;
        this.resource = resource;
    }

    /**
     * Returns the resource of this item.
     *
     * @return resource is a String.
     */
    public String getResource(){
        return this.resource;
    }

    /**
     * Sets the URL of this item to the parameter String URL.
     *
     * @param URL is a String.
     */
    public void setURL(String URL){
        this.URL = URL;
    }

    /**
     * Returns the URL of this item.
     *
     * @return URL is a String.
     */
    public String getURL(){
        return URL;
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

}
