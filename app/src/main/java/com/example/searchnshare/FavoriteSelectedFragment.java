package com.example.searchnshare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

/**
 * This fragment is loaded into the view when an item in the Favorite Fragment is selected. This fragment
 * has a one button up top to share the post and a webview which will contain the Url of the current
 * post. The webview will be loaded wih the url when the fragment is opened.
 */
public class FavoriteSelectedFragment extends Fragment {



    private String URL;


    public FavoriteSelectedFragment(String URL){
        this.URL = URL;
    }



    /**
     * This method is called when the FavoriteSelectedFragment comes into view. It will fetch the webview
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
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_favorite_item_selected, container, false);
        WebView wb = v.findViewById(R.id.webViewFav);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setBuiltInZoomControls(false);

        wb.loadUrl(URL);
        return v;
    }

    /**
     * Returns the URL of the current post.
     *
     * @return
     */
    public String getURL() {
        return URL;
    }
}
