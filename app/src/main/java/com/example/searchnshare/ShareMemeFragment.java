package com.example.searchnshare;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;


public class ShareMemeFragment extends Fragment {

    public ImageView memeImage;
    public String memeUrl;
    public String webUrl;
    public WebView myWebView;


    public ShareMemeFragment(ImageView memeView, String url, String webViewUrl) {
        memeImage = memeView;
        memeUrl = url;
        webUrl = webViewUrl;
    }


    public void loadWebView(){
        System.out.println("MEME URL IN WEBVIEW---" + webUrl);

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setBuiltInZoomControls(false);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(webUrl);

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
        View v = inflater.inflate(R.layout.fragment_share_meme, container, false);
        myWebView = v.findViewById(R.id.memeWebView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            myWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        loadWebView();
        return v;
    }

}
