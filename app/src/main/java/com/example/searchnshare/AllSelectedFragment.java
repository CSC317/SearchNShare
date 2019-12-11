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
public class AllSelectedFragment extends Fragment {

    public Activity containerActivity = null;
    private String AnyUrl;
    private String title;
    private Bitmap image;
    private String website;
    private String content;

    public AllSelectedFragment() {}

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
        View v = inflater.inflate(R.layout.fragment_all_selected, container, false);
        WebView myWebView = v.findViewById(R.id.anyWebView);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(AnyUrl);
        return v;
    }

    public void setAnyUrl(String url) {
        this.AnyUrl = url;
    }

    public void setAnyTitle(String title) {
        this.title = title;
    }

    public void setAnyImage(Bitmap image) {
        this.image = image;
    }

    public String getAnyUrl() {
        return AnyUrl;
    }

    public String getAnyTitle() {
        return title;
    }

    public Bitmap getAnyImage() {
        return image;
    }

    public void setAnyWebsite(String website) {
        this.website = website;
    }

    public void setAnyContentStr(String content) {
        this.content = content;
    }

    public String getAnyContentStr() {
        return content;
    }

    public String getAnyWebsite() {
        return website;
    }

}
