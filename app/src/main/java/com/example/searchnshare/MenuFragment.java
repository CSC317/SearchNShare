package com.example.searchnshare;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    public Activity containerActivity = null;

    public MenuFragment() { }

    /**
     * Sets the container activity to the activity passed as a parameter.
     *
     * @param containerActivity the current activity.
     */
    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }


    /**
     * This method is used to set the view for this fragment
     * @param inflater inflater used to inflate view
     * @param container the container
     * @param savedInstanceState bundle of instance state
     * @return view to open
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        EditText field = v.findViewById(R.id.text_field);
        field.setText(MainActivity.search);
        return v;
    }

    /**
     * This method is used in order to load the webview of the content for this fragment
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.help_menu, menu);
        return;
    }

}
