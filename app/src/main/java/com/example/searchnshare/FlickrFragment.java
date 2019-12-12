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
 * This fragment shows the Flickr API in a single list view. It gets the most recent search term and
 * runs a query on the Flickr API to then fill the list view from its results.
 */
public class FlickrFragment extends Fragment {

    public Activity containerActivity = null;
    public String urlBeginning = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=c68268937007bf1cc8ac4be2d67276b0&text=";
    public String urlEnd = "&extras=url_c%2C+date_upload&per_page=10&format=json&nojsoncallback=1";

    public FlickrFragment() {}

    /**
     * Sets the container activity to the activity passed as a parameter.
     *
     * @param containerActivity the current activity.
     */
    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }

    /**
     * This method is called when the Flickr Fragment comes into the view. This fragment has a menu at
     * the top of the screen, and the text of the Edit Text of this fragment is set to the current
     * most recent search term. The GetImagesTask async task is then executed and the listView is filled
     * with posts from the Flickr API.
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
        View v = inflater.inflate(R.layout.fragment_flickr, container, false);
        EditText field = v.findViewById(R.id.flickr_field);
        field.setText(MainActivity.search);
        new GetImagesTask().execute();
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
            System.out.println("Im here");
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            input.close();
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method runs the GetImagesTask async task when the search button on the Flickr Fragment
     * is pressed.
     */
    public void showFragSearch() {
        new GetImagesTask().execute();
    }

    /**
     * This class fills the list view of this flickr fragment with posts from the Flickr API.
     */
    public class GetImagesTask extends AsyncTask<Object, Void, List<FlickrRowItem>> {

        /**
         * Fetches the JSONObject and fills the rowItems ArrayList with FlickrRowItems with the correct
         * information for each post.
         *
         * @param objs
         * @return a List of FlickrRowItems
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
                        String owner = photoList.getJSONObject(i).getString("owner");
                        String id = photoList.getJSONObject(i).getString("id");
                        String postUrl = "https://flickr.com/photos/" + owner + "/" + id;

                        Bitmap imageBitmap = getBitmapFromURL(imageUrl);
                        System.out.println("Hello?");
                        FlickrRowItem rowItem = new FlickrRowItem(title, imageBitmap, postUrl);
                        rowItems.add(rowItem);


//                        HashMap<String, String> hm = new HashMap<String, String>();
//                        hm.put("flickr_row_title", title);
//                        hm.put("flickr_row_image", imageBitmap.toString());
//                        imageList.add(hm);
                    }
                }
                return rowItems;

            } catch (Exception e) { e.printStackTrace(); }

            return null;
        }

        /**
         * Sets the adapter of the imageListView to a new CustomAdapter to fill the list view with
         * Flickr Posts.
         *
         * @param aList a List of FlickrRowItem for the CustomAdapter.
         */@Override
        protected void onPostExecute(List<FlickrRowItem> aList) {
            try {
//                String[] from = {"flickr_row_title", "flickr_row_image"};
//                int[] to = {R.id.flickr_row_title, R.id.flickr_row_image};



                CustomAdapter adapter = new CustomAdapter(containerActivity,
                        R.layout.flickr_row, aList);


                ListView imageListView = (ListView) containerActivity.findViewById(R.id.image_list);
                imageListView.setAdapter(adapter);


            } catch (Exception e) { e.printStackTrace(); }
        }
    }

}
