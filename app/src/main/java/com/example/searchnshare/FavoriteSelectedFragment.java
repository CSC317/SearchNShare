package com.example.searchnshare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

public class FavoriteSelectedFragment extends Fragment {



    private String URL;


    public FavoriteSelectedFragment(String URL){
        this.URL = URL;
    }



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

    public String getURL() {
        return URL;
    }
}
