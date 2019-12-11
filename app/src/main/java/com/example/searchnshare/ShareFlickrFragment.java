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
 * A simple {@link Fragment} subclass.
 */
public class ShareFlickrFragment extends Fragment {

    public Activity containerActivity = null;
    private String flickrUrl;
    private String title;
    private Bitmap image;

    public ShareFlickrFragment() {}

    /**
     * Sets the container activity to the activity passed as a parameter.
     *
     * @param containerActivity the current activity.
     */
    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_share_flickr, container, false);
        WebView myWebView = v.findViewById(R.id.flickrWebView);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(flickrUrl);
        return v;
    }

    public void setFlickrUrl(String url) {
        this.flickrUrl = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getFlickrUrl() {
        return flickrUrl;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getImage() {
        return image;
    }

}
