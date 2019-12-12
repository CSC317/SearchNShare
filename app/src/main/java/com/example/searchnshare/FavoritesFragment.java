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
 * This fragment shows all four APIs in a single list view. It starts off empty, but any post the
 * user likes can be added as the last item the list view.
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

    /**
     * This method sets the currentFavs ArrayList to the parameter favs.
     *
     * @param favs is an ArrayList<FavoriteListItem>.
     */
    public void setFavs(ArrayList<FavoriteListItem> favs){
        this.currentFavs = favs;
    }

    /**
     * This method sets the ourAdapter FavoritesAdapter to the parameter ourAdapter.
     *
     * @param ourAdapter is a FavoritesAdapter object.
     */
    public void setAdapter(FavoritesAdapter ourAdapter){
        this.ourAdapter = ourAdapter;
    }

    /**
     * This method is called when the Favorites Fragment comes into the view. This fragment has a menu at
     * the top of the screen, and ourAdapter is set to the adapter of the list view of the fragment.
     * The listview also has an onItemClickListener.
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

    /**
     * This method tells this fragment which menu to show at the top of the screen.
     *
     * @param menu is a Menu object.
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_menu, menu);
        return;
    }

}
