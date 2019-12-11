package com.example.searchnshare;


import android.app.Activity;
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

}
