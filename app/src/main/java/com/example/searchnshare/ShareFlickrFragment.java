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


    /**
     * This method is used to set the view for this fragment
     * @param inflater inflater used to inflate view
     * @param container the container
     * @param savedInstanceState bundle of instance state
     * @return view to open
     */
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

    /**
     * Setter for the url
     * @param url String for url
     */
    public void setFlickrUrl(String url) {
        this.flickrUrl = url;
    }

    /**
     * Setter for the new title
     * @param title String the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for the image to populate
     * @param image Bitmap to reference
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * GEtter for the url
     * @return STring url to open
     */
    public String getFlickrUrl() {
        return flickrUrl;
    }

    /**
     * getter for the current title of this item
     * @return STring the title for this item
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the current bitmap attached tothis item
     * @return Bitmap that is attached to this item
     */
    public Bitmap getImage() {
        return image;
    }

}
