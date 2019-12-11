package com.example.searchnshare;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    public Activity containerActivity = null;

    private ArrayList<FavoriteListItem> currentFavs;


    private FavoritesAdapter ourAdapter;
    public FavoritesFragment() {}

    /**
     * Sets the container activity to the activity passed as a parameter.
     *
     * @param containerActivity the current activity.
     */
    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }

    public void setFavs(ArrayList<FavoriteListItem> favs){
        this.currentFavs = favs;
    }

    public void setAdapter(FavoritesAdapter ourAdapter){
        this.ourAdapter = ourAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);
        ListView LV = v.findViewById(R.id.favorites_list);
        LV.setAdapter(ourAdapter);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FavoriteSelectedFragment newFrag = new FavoriteSelectedFragment(currentFavs.get(position).getURL());
                ((MainActivity)containerActivity).setFavSelectedFragment(newFrag);
                FragmentTransaction fragTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragTransaction.replace(R.id.main_inner, newFrag);
                fragTransaction.addToBackStack(null);
                fragTransaction.commit();
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_menu, menu);
        return;
    }

}
