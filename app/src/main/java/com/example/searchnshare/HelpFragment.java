package com.example.searchnshare;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * This fragment shows pointers on how to use the app for the user and/or the grader.
 */
public class HelpFragment extends Fragment {


    public HelpFragment() {}

    /**
     * This method is called when the Help Fragment comes into the view.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        return v;
    }

}
