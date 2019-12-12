package com.example.searchnshare;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * This fragment is loaded into the view when an item in the ALL Fragment is selected. This fragment
 * has a two buttons up top to either share the post or add the post to favorites and a webview which
 * will contain the Url of the current post. The webview will be loaded wih the url when the fragment
 * is opened.
 */
public class AllSelectedFragment extends Fragment {

    public Activity containerActivity = null;
    private String AnyUrl;
    private String title;
    private Bitmap image;

    public AllSelectedFragment() {}

    /**
     * Sets the container activity to the activity passed as a parameter.
     *
     * @param containerActivity the current activity.
     */
    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }

    /**
     * This method is called when the AllSelectedFragment comes into view. It will fetch the webview
     * of the fragment and load the Url of the current post.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_selected, container, false);
        WebView myWebView = v.findViewById(R.id.anyWebView);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(AnyUrl);
        return v;
    }

    /**
     * Thi method sets the url of the current post.
     *
     * @param url is a String.
     */
    public void setAnyUrl(String url) {
        this.AnyUrl = url;
    }

    /**
     * Thi method sets the title of the current post.
     *
     * @param title is a String.
     */
    public void setAnyTitle(String title) {
        this.title = title;
    }

    /**
     * Thi method sets the image of the current post.
     *
     * @param image is a Bitmap.
     */
    public void setAnyImage(Bitmap image) {
        this.image = image;
    }

    /**
     * Thi method returns the url of the current post.
     */
    public String getAnyUrl() {
        return AnyUrl;
    }

    /**
     * Thi method returns the title of the current post.
     */
    public String getAnyTitle() {
        return title;
    }

    /**
     * Thi method returns the image of the current post.
     */
    public Bitmap getAnyImage() {
        return image;
    }


}
