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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
public class FlickrFragment extends Fragment {

    public Activity containerActivity = null;
    public String urlBeginning = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=c68268937007bf1cc8ac4be2d67276b0&text=";
    public String urlEnd = "&extras=url_c%2C+date_upload&per_page=30&format=json&nojsoncallback=1";

    public FlickrFragment() {}

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
        View v = inflater.inflate(R.layout.fragment_flickr, container, false);
        EditText field = v.findViewById(R.id.flickr_field);
        field.setText(MainActivity.search);
        new GetImagesTask().execute();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_menu, menu);
        return;
    }

    /**
     * Returns a bitmap for the given URL as a parameter.
     *
     * @param src is a URl as a string.
     *
     * @return a bitmap for the given URL.
     */
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showFragSearch() {
        new GetImagesTask().execute();
    }

    public class GetImagesTask extends AsyncTask<Object, Void, List<FlickrRowItem>> {

        /**
         * Creates a new JSON object with the current url with the given search term.
         * Fills the contents of a String[][] with the articles information for later use.
         * Uses a simpleAdapter for conversion into the ListView.
         *
         * @param objs
         * @return the List of HashMaps for the simpleAdapter.
         */
        @Override
        protected List<FlickrRowItem> doInBackground(Object[] objs) {
            try {

                String json = "";
                String line;

                URL url = new URL(urlBeginning + MainActivity.search + urlEnd);

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                while ((line = in.readLine()) != null) {
                    json += line;
                }
                in.close();

                JSONObject jsonObject = new JSONObject(json);
                JSONObject photos = jsonObject.getJSONObject("photos");
                JSONArray photoList = photos.getJSONArray("photo");



                List<HashMap<String, String>> imageList = new ArrayList<HashMap<String, String>>();
                List<FlickrRowItem> rowItems = new ArrayList<>();

                for (int i = 0; i < photoList.length(); i++) {
                    if (photoList.getJSONObject(i).has("url_c")) {
                        String title = photoList.getJSONObject(i).getString("title");
                        String imageUrl = photoList.getJSONObject(i).getString("url_c");
                        Bitmap imageBitmap = getBitmapFromURL(imageUrl);

                        FlickrRowItem rowItem = new FlickrRowItem(title, imageBitmap);
                        rowItems.add(rowItem);


                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("flickr_row_title", title);
                        hm.put("flickr_row_image", imageBitmap.toString());
                        imageList.add(hm);
                    }
                }
                return rowItems;
//                return imageList;

            } catch (Exception e) { e.printStackTrace(); }

            return null;
        }

        /**
         *
         *
         * @param aList a List of HashMaps for the simpleAdapter.
         */
        @Override
        protected void onPostExecute(List<FlickrRowItem> aList) {
            try {
                String[] from = {"flickr_row_title", "flickr_row_image"};
                int[] to = {R.id.flickr_row_title, R.id.flickr_row_image};

//                SimpleAdapter simpleAdapter =
//                        new SimpleAdapter(containerActivity, aList,
//                                R.layout.flickr_row, from, to);

                CustomAdapter adapter = new CustomAdapter(containerActivity,
                        R.layout.flickr_row, aList);


                ListView imageListView = (ListView) containerActivity.findViewById(R.id.image_list);
//                tierListView.setAdapter(simpleAdapter);
                imageListView.setAdapter(adapter);


            } catch (Exception e) { e.printStackTrace(); }
        }
    }

}
