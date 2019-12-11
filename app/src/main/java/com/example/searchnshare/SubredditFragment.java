package com.example.searchnshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

public class SubredditFragment extends Fragment {

    private String urlToOpen; // URL to open in webView
    private String subredditToOpen; // Subreddit to open through an intent

    private WebView myWebView;

    private String title;

    /**
     *
     * @param urlToOpen String of the URL to open
     */
    public SubredditFragment(String urlToOpen, String subreddit){
        this.urlToOpen = urlToOpen;
        this.subredditToOpen = subreddit;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void openSubreddit(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.reddit.com/"+subredditToOpen));
        startActivity(browserIntent);
    }

    public void loadWebView(){
        System.out.println(urlToOpen);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setBuiltInZoomControls(false);

        myWebView.loadUrl(urlToOpen);

    }


    /** Opens a webView of a URL
     * @param inflater LayoutInflater used to inflate the layout
     * @param container ViewGroup of where the view is contained
     * @param savedInstanceState Bundle of the instance state
     * @return v View of the view that was created
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_subreddit_selection, container, false);
        myWebView = v.findViewById(R.id.webView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        loadWebView();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_menu, menu);
        return;
    }
}
