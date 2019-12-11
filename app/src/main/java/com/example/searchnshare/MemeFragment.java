package com.example.searchnshare;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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
    private Activity containerActivity = null;
    private String memeUrl = "https://meme-api.herokuapp.com/gimme/";

    private String APIkey = "/10";
    private ListView memeListView;
    private ContactsFragment contactsFragment;
    private List<HashMap<String, String>> memeArrayList;
    private String fullUrl;
    private ShareMemeFragment shareMemeFrag;
    private String jpgMeme;
    private String memeWebUrl;
    private ImageView memeClickedImageView;
    private String memeClickedtitle;
    private Bitmap memeBitmap;


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

    /**
     * Getter for current Activity
     * @return Activity current activity
     */
    public Activity getContainerActivity() {
        return this.containerActivity;
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
        View v = inflater.inflate(R.layout.fragment_meme, container, false);
        EditText field = v.findViewById(R.id.meme_field);
        field.setText(MainActivity.search);
        memeListView = v.findViewById(R.id.memes_list);
        memeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> map = memeArrayList.get(position);
                memeWebUrl = map.get("meme_web_view");
                memeClickedtitle = map.get("meme_row_title");
                ImageView collageView = view.findViewById(R.id.meme_row_image);
                memeClickedImageView = collageView;
                Bitmap bitmap = Bitmap.createBitmap(
                        collageView.getWidth(), collageView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                collageView.draw(canvas);
                shareMemeFrag = new ShareMemeFragment(memeWebUrl);
                memeBitmap = bitmap;
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_inner, shareMemeFrag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        new GetMemesTask().execute();
        return v;
        ///just send the link, don't bother making the imageFile, delete below functions
    }

    /**
     * getter for the full url for this item
     * @return String full url to attached
     */
    public String getFullUrl(){
        return this.fullUrl;
    }

    public ShareMemeFragment getShareMemeFrag(){
        return this.shareMemeFrag;
    }

    public ImageView getMemeClickedImageView(){
        return this.memeClickedImageView;
    }

    /**
     * This is used to attach the menu item
     * @param menu menu item created
     * @param inflater this is reernced from
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_menu, menu);
        return;
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            input.close();
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Execute a new search
     */
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
                fullUrl = memeUrl + searchText + APIkey;
                URL url = new URL(fullUrl);

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                while ((line = in.readLine()) != null) {
                    json += line;
                }
                in.close();

                JSONObject jsonObject = new JSONObject(json);
                JSONArray memeList = jsonObject.getJSONArray("memes");

                memeArrayList = new ArrayList<HashMap<String, String>>();
                List<MemeRowItem> rowItems = new ArrayList<>();


                for (int i = 0; i < memeList.length(); i++) {
                    if (memeList.getJSONObject(i).has("url")) {
                        String title = memeList.getJSONObject(i).getString("title");
                        String imageUrl = memeList.getJSONObject(i).getString("url");
                        String memeWebView = memeList.getJSONObject(i).getString("postLink");
                        jpgMeme = imageUrl;
                        Bitmap imageBitmap = getBitmapFromURL(imageUrl);

                        MemeRowItem rowItem = new MemeRowItem(title, imageBitmap);
                        rowItems.add(rowItem);

                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("meme_row_title", title);
                        hm.put("meme_row_image", imageBitmap.toString());
                        hm.put("meme_web_view", memeWebView);
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

    /**
     * String for the mem item that was clicked
     * @return String of the meme title
     */
    public String getMemeClickedtitle(){
        return this.memeClickedtitle;
    }

    /**
     * getter for a Bitmap that is attached
     * @return Bitmap attached to this item
     */
    public Bitmap getMemeBitmap(){
        return this.memeBitmap;
    }

    /**
     * getter for the url attached to this item
     * @return String that is attached to this tem
     */
    public String getMemeWebUrl()
    {
        return this.memeWebUrl;
    }
}

