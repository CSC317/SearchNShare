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

    public Activity containerActivity = null;
    private String newsUrl;
    private String website;
    private String content;
    private Bitmap image;

    public ShareNewsFragment() {}

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
        View v = inflater.inflate(R.layout.fragment_share_news, container, false);
        WebView myWebView = v.findViewById(R.id.newsWebView);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(newsUrl);
        return v;
    }

    public void setNewsUrl(String url) {
        this.newsUrl = url;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setContentStr(String content) {
        this.content = content;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getContentStr() {
        return content;
    }

    public String getWebsite() {
        return website;
    }

}
