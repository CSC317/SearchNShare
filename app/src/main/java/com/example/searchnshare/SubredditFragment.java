package com.example.searchnshare;

import android.app.Activity;
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

    private WebView myWebView; // webview item

    private String title; //title of item
    private Activity containterActivity; //container activity

    /**
     *
     * @param urlToOpen String of the URL to open
     */
    public SubredditFragment(String urlToOpen, String subreddit){
        this.urlToOpen = urlToOpen;
        this.subredditToOpen = subreddit;
    }

    /**
     * Getter used to get a reference to the title
     * @return String title for this item
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Setter used to set the container activity from the activity in which it is held
     * @param containterActivity Activity type to reference
     */
    public void setContainterActivity(Activity containterActivity){
        this.containterActivity = containterActivity;
    }

    /**
     * Getter used to reference the container activity
     * @return Activity type this activity for the fragment
     */
    public Activity getContainterActivity(){
        return this.containterActivity;
    }

    /**
     *
     * @param  title String used to set the title for this item
     */
    public void setTitle(String title){
        this.title = title;
    }


    /**
     * This method will be used as a getter to find the reference to the url that is to be
     * opened
     * @return String url that is the user posting url
     */
    public String getUrlToOpen(){
        return urlToOpen;
    }

    /**
     * This methos will be used and called from main activity when the user wants
     * to view a subreddit
     */
    public void openSubreddit(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.reddit.com/"+subredditToOpen));
        startActivity(browserIntent);
    }

    /**
     * This methid will be used to open the webview of the url that was attached to this fragment
     */
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

    /**
     * This is used to attach the menu item
     * @param menu menu item created
     * @param inflater this is reernced from
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_menu, menu);
        return;
    }
}
