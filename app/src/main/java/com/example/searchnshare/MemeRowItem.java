package com.example.searchnshare;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;

public class MemeRowItem{

    private Bitmap imageBitmap;
    private String title;

    public MemeRowItem(String title, Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
        this.title = title;
    }

    /**
     * This method will return the curent bitmap
     * @return Bitmap that is attached to this item
     */
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }
    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    /**
     * used to get the title
     * @return String title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Used to set the title
     * @param title String to set tile
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
