package com.example.searchnshare;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ArrayAdapter;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * This fragment shows all four APIs in a single list view. The order of the posts in the list view
 * are Reddit, News, Flickr, and then the Meme posts.
 */
public class AllFragment extends Fragment {

    private String RedditURL = "https://www.reddit.com/r/";
    private String RedditURLTerm = "BabyShark";
    private String endURL = "/new.json?limit=25";

    public Activity containerActivity = null;
    public Calendar rightNow = Calendar.getInstance();
    public String startDate = "" + rightNow.YEAR + "-" + rightNow.MONTH + "-" + (rightNow.DAY_OF_MONTH - 7);
    public String NewsUrlBeginning = "https://newsapi.org/v2/everything?sortBy=publishedAt&q=";
    public String NewsUrlMid = "&from=" + startDate;
    public String NewsUrlEnd = "&apiKey=19cb84b5a36f4d1cb00e290e84a93eeb";

    public String FlickrUrlBeginning = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=c68268937007bf1cc8ac4be2d67276b0&text=";
    public String FlickrUrlEnd = "&extras=url_c%2C+date_upload&per_page=10&format=json&nojsoncallback=1";

    public String memeUrl = "https://meme-api.herokuapp.com/gimme/";
    public String MemeAPIkey = "/10";

    private ArrayList<FavoriteListItem> allList;
    private FavoritesAdapter AllAdapter;

    public AllFragment() {}

    /**
     * Sets the container activity to the activity passed as a parameter.
     *
     * @param containerActivity the current activity.
     */
    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }

    /**
     * This method is called when the All Fragment comes into the view. This fragment has a menu at
     * the top of the screen, and the text of the Edit Text of this fragment is set to the current
     * most recent search term. The GetAllTask async task is then executed and the listView is filled
     * with posts from all four APIs.
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
        View v = inflater.inflate(R.layout.fragment_all, container, false);
        EditText field = v.findViewById(R.id.all_field);
        field.setText(MainActivity.search);
        new GetAllTask().execute();
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
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            input.close();
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns a JSONArray for the Reddit API.
     *
     * @return a JSONArray object.
     */
    private JSONArray getRedditInfo() {
        try {
            String json = "" ;
            if (MainActivity.search.contains(" ")) {
                String[] searchTerms = MainActivity.search.split(" ");
                RedditURLTerm = "";
                for (int i = 0; i<searchTerms.length;i++){
                    RedditURLTerm += searchTerms[i];
                    RedditURLTerm += "+";
                }
            }
            else {
                RedditURLTerm = MainActivity.search;
            }
            URL url = new URL(RedditURL+RedditURLTerm+endURL);
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String hold;
                while ((hold = in.readLine()) != null){
                    json += hold;
                }
                JSONObject redditJSON = new JSONObject(json);
                JSONObject arrayPointer = redditJSON.getJSONObject("data");
                JSONArray articleArray =  arrayPointer.getJSONArray("children");
                in.close();
                return articleArray;

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /**
     * This method returns a JSONArray for the News API.
     *
     * @return a JSONArray object.
     */
    private JSONArray getNewsInfo() {
        try {

            String json = "";
            String line;

            URL url = new URL(NewsUrlBeginning + MainActivity.search + NewsUrlMid + NewsUrlEnd);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = in.readLine()) != null) {
                json += line;
            }
            in.close();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray articleList = jsonObject.getJSONArray("articles");

            return articleList;

        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }

    /**
     * This method returns a JSONArray for the Flickr API.
     *
     * @return a JSONArray object.
     */
    private JSONArray getFlickrInfo() {
        try {
            String json = "";
            String line;

            URL url = new URL(FlickrUrlBeginning + MainActivity.search + FlickrUrlEnd);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = in.readLine()) != null) {
                json += line;
            }
            in.close();

            JSONObject jsonObject = new JSONObject(json);
            JSONObject photos = jsonObject.getJSONObject("photos");
            JSONArray photoList = photos.getJSONArray("photo");

            return photoList;
        }

        catch (Exception e) { e.printStackTrace(); }

        return null;
    }

    /**
     * This method returns a JSONArray for the Meme API.
     *
     * @return a JSONArray object.
     */
    private JSONArray getMemeInfo() {
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
            URL url = new URL(memeUrl + searchText + MemeAPIkey);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = in.readLine()) != null) {
                json += line;
            }
            in.close();

            JSONObject jsonObject = new JSONObject(json);
            JSONArray memeList = jsonObject.getJSONArray("memes");

            return memeList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method fills the list view of this fragment with posts from all four APIs. It uses an
     * array of JSONArray objects, one from each API, to fill the list view. The list view will
     * contain at most, 3 posts from each API for performance purposes.
     *
     * @param array is an array of JSONArray objects
     */
    private void fillAllList(JSONArray[] array) {
        int redditIndex = 0;
        int newsIndex = 0;
        int flickrIndex = 0;
        int memeIndex = 0;
        int index = 0;
        while (index < 12) {
            try {
                if (index == 0 || index == 4 || index == 8) {
                    JSONObject data = array[0].getJSONObject(redditIndex).getJSONObject("data");
                    if (data.has("url")) {
                        String title = "This post has no title.";
                        if (data.has("title")) {
                            title = data.getString("title");
                        }
                        String subreddit_name_prefixed = "No prefix found.";
                        if (data.has("subreddit_name_prefixed")) {
                            subreddit_name_prefixed = data.getString("subreddit_name_prefixed");
                        }
                        String permalink = "";
                        if (data.has("permalink")) {
                            permalink = data.getString("permalink");
                        }

                        FavoriteListItem item = new FavoriteListItem(title + "\n" + subreddit_name_prefixed, null, "Reddit");
                        item.setURL("https://www.reddit.com" + permalink);
                        allList.add(item);

                    }
                    redditIndex++;
                }
                if (index == 1 || index == 5 || index == 9) {
                    String website  = array[1].getJSONObject(newsIndex).getJSONObject("source").getString("name");
                    String content = array[1].getJSONObject(newsIndex).getString("content");
                    String imageUrl = array[1].getJSONObject(newsIndex).getString("urlToImage");
                    String postUrl = array[1].getJSONObject(newsIndex).getString("url");
                    Bitmap image;
                    image = getBitmapFromURL(imageUrl);
                    FavoriteListItem item = new FavoriteListItem("NEWS\n" + website + "\n" + content, image, "news");
                    item.setURL(postUrl);
                    allList.add(item);
                    newsIndex++;
                }
                if (index == 2 || index == 6 || index == 10) {
                    if (array[2].getJSONObject(flickrIndex).has("url_c")) {
                        String title = array[2].getJSONObject(flickrIndex).getString("title");
                        String imageUrl = array[2].getJSONObject(flickrIndex).getString("url_c");
                        String owner = array[2].getJSONObject(flickrIndex).getString("owner");
                        String id = array[2].getJSONObject(flickrIndex).getString("id");
                        String postUrl = "https://flickr.com/photos/" + owner + "/" + id;

                        Bitmap imageBitmap = getBitmapFromURL(imageUrl);

                        FavoriteListItem item = new FavoriteListItem("FLICKR\n"+title, imageBitmap, "flickr");
                        item.setURL(postUrl);
                        allList.add(item);
                    }
                    flickrIndex++;
                }
                if (index == 3 || index == 7 || index == 11) {
                    if (array[3].getJSONObject(memeIndex).has("url")) {
                        String title = array[3].getJSONObject(memeIndex).getString("title");
                        String imageUrl = array[3].getJSONObject(memeIndex).getString("url");
                        String memePostUrl = array[3].getJSONObject(memeIndex).getString("postLink");
                        Bitmap imageBitmap = getBitmapFromURL(imageUrl);

                        FavoriteListItem item = new FavoriteListItem("MEME\n"+title, imageBitmap, "meme");
                        item.setURL(memePostUrl);
                        allList.add(item);
                    }
                    memeIndex++;
                }
            }
            catch (Exception e) { e.printStackTrace(); }
            index++;
        }
    }

    /**
     * This method runs the GetAllTask async task when the search button on the ALL Fragment is pressed.
     */
    public void showFragSearch() {
        new GetAllTask().execute();
    }

    /**
     * This class fills the list view of this all fragment with posts from all four APIs.
     */
    public class GetAllTask extends AsyncTask<Object, Void, Object[]> {

        /**
         * Runs all four methods to fetch the JSONArray from each API and fills the allList ArrayList
         * for the Listview. Also sets the AllAdapter to a new FavoritesAdapter.
         *
         * @param objs
         * @return null
         */
        @Override
        protected JSONArray[] doInBackground(Object[] objs) {
            allList = new ArrayList<>();

            JSONArray[] apis = new JSONArray[4];

            JSONArray reddit = getRedditInfo();
            apis[0] = reddit;

            JSONArray news = getNewsInfo();
            apis[1] = news;

            JSONArray flickr = getFlickrInfo();
            apis[2] = flickr;

            JSONArray memes = getMemeInfo();
            apis[3] = memes;

            fillAllList(apis);

            AllAdapter = new FavoritesAdapter(containerActivity, R.id.favorite_row, allList);

            return null;
        }

        /**
         * This method sets the adapter of the listView to the AllAdapter and sets the o
         * nItemClickListener for the list view.
         *
         * @param aList is not used.(null)
         */@Override
        protected void onPostExecute(Object[] aList) {
            try {

                ListView allListView = (ListView) containerActivity.findViewById(R.id.all_list);
                allListView.setAdapter(AllAdapter);
                allListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView urlView = view.findViewById(R.id.favorite_row_url);
                        TextView titleView = view.findViewById(R.id.favorite_row_title);
                        ImageView imageView = view.findViewById(R.id.favorite_row_image);
                        String url = urlView.getText().toString();
                        String title = titleView.getText().toString();
                        Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        AllSelectedFragment frag = new AllSelectedFragment();
                        ((MainActivity)containerActivity).setAllSelectedFragment(frag);
                        frag.setAnyUrl(url);
                        frag.setAnyTitle(title);
                        frag.setAnyImage(image);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        frag.setContainerActivity(containerActivity);
                        transaction.replace(R.id.main_inner, frag);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

            } catch (Exception e) { e.printStackTrace(); }
        }
    }

}
