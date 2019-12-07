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
