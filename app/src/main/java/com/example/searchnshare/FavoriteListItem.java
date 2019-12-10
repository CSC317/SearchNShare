package com.example.searchnshare;

import android.graphics.Bitmap;

public class FavoriteListItem {

    private Bitmap imageBitmap;
    private String title;
    //private String descrpition;
    private String resource;
    private String URL;


    public FavoriteListItem(String title, Bitmap imageBitmap, String resource ) {
        this.imageBitmap = imageBitmap;
        this.title = title;
        //this.descrpition = descrpition;
        this.resource = resource;
    }

    public String getResource(){
        return this.resource;
    }

    public void setURL(String URL){
        this.URL = URL;
    }

    public String getURL(){
        return URL;
    }

//    public String getDescpition(){
//        return this.descrpition;
//    }

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
