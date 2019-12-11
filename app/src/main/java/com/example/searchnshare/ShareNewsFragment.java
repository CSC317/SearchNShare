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
public class ShareNewsFragment extends Fragment {

    public Activity containerActivity = null; // Activity to reference
    private String newsUrl; // url to news
    private String website; // url to website
    private String content; // content of article


    public ShareNewsFragment() {}

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
        View v = inflater.inflate(R.layout.fragment_share_news, container, false);
        WebView myWebView = v.findViewById(R.id.newsWebView);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(newsUrl);
        return v;
    }

    /**
     * Setter for url new
     * @param url String url for this item
     */
    public void setNewsUrl(String url) {
        this.newsUrl = url;
    }

    /**
     * Used to set the website
     * @param website String the website to open
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Setter for the contents of this item
     * @param content String content for this item
     */
    public void setContentStr(String content) {
        this.content = content;
    }

    /**
     * String url for this item
     * @return String url for this selcted item
     */
    public String getNewsUrl() {
        return newsUrl;
    }

    /**
     * Getter for content
     * @return STring content of item
     */
    public String getContentStr() {
        return content;
    }

    /**
     * Getter for website
     * @return String website to item
     */
    public String getWebsite() {
        return website;
    }

}
