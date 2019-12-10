package com.example.searchnshare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ShareMemeFragment extends Fragment {

    ImageView memeImage;
    String memeUrl;

    public ShareMemeFragment(ImageView memeView, String url) {
        memeImage = memeView;
        memeUrl = url;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_share_meme, container, false);
    }




}
