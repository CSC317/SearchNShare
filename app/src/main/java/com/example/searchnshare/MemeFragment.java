package com.example.searchnshare;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemeFragment extends Fragment {
    public Activity containerActivity = null;
    public String memeUrl = "https://meme-api.herokuapp.com/gimme/";
    public String APIkey = "/10";

    //TYPE IN + IN BETWEEN SPACES OF A SEARCH

    public MemeFragment() {
    }

    /**
     * Sets the container activity to the activity passed as a parameter.
     *
     * @param containerActivity the current activity.
     */
    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_meme, container, false);
        EditText field = v.findViewById(R.id.meme_field);
        field.setText(MainActivity.search);
        new GetMemesTask().execute();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_menu, menu);
        return;
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            System.out.println("POOOOP");
            connection.setDoInput(true);
            connection.connect();
            System.out.println("POOOOP");
            InputStream input = connection.getInputStream();
            System.out.println("POOOOP");
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            input.close();
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void showFragSearch() {
        new MemeFragment.GetMemesTask().execute();
    }

    public class GetMemesTask extends AsyncTask<Object, Void, List<MemeRowItem>> {
        @Override
        protected List<MemeRowItem> doInBackground(Object[] objs) {
            try {

                String json = "";
                String line;
                String searchText = MainActivity.search;

                if (searchText.contains(" ")) {
                    String[] searchTerms = searchText.split(" ");
                    searchText = "";
                    for (int i = 0; i<searchTerms.length;i++){
                        searchText += searchTerms[i];
                        searchText += "+";
                    }
                }
                URL url = new URL(memeUrl + searchText + APIkey);

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                while ((line = in.readLine()) != null) {
                    json += line;
                }
                in.close();

                JSONObject jsonObject = new JSONObject(json);
                JSONArray memeList = jsonObject.getJSONArray("memes");

                List<HashMap<String, String>> memeArrayList = new ArrayList<HashMap<String, String>>();
                List<MemeRowItem> rowItems = new ArrayList<>();


                for (int i = 0; i < memeList.length(); i++) {
                    if (memeList.getJSONObject(i).has("url")) {
                        String title = memeList.getJSONObject(i).getString("title");
                        String imageUrl = memeList.getJSONObject(i).getString("url");

                        System.out.println(title);
                        System.out.println(imageUrl);
                        Bitmap imageBitmap = getBitmapFromURL(imageUrl);

                        MemeRowItem rowItem = new MemeRowItem(title, imageBitmap);
                        rowItems.add(rowItem);

                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("meme_row_title", title);
                        hm.put("meme_row_image", imageBitmap.toString());
                        memeArrayList.add(hm);
                    }

                }
                return rowItems;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
        }
        @Override
        protected void onPostExecute(List<MemeRowItem> aList) {
            try {
                String[] from = {"meme_row_title", "meme_row_image"};
                int[] to = {R.id.meme_row_title, R.id.meme_row_image};

               MemeAdapter adapter = new MemeAdapter(containerActivity,
                        R.layout.meme_row, aList);

                ListView imageListView = (ListView) containerActivity.findViewById(R.id.memes_list);
                imageListView.setAdapter(adapter);

            } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
