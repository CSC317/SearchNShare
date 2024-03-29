package com.example.searchnshare;


import android.app.Activity;
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
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RedditFragment extends Fragment {

    private ListView redditListView; // ListView reference to attach content to
    private ArrayAdapter<String> articleArrayAdapter = null; // ArrayAdapter used to set Listview
    private String RedditURL = "https://www.reddit.com/r/";
    //private String RedditURL = "https://www.reddit.com/subreddits/search.json?q=";
    private String RedditURLTerm = "BabyShark";
    private String endURL = "/new.json?limit=25";
    private ArrayList<String> titleRedditSubreddits; //Arraylist of titles
    private ArrayList<String> urlRedditSubreddits;//arraylist of urls
    private ArrayList<String> permalinkRedditSubreddits;// arraylist of permalink
    private ArrayList<String> prefixRedditSubreddits;// arraylist of subreddits
    private Activity containerActivity; // Activity to which this fragment is contained in

    private String currentFragmentPermalink; // current permalink
    private String currentFragmentSubreddit; // current subreddit
    private String urlToOpen; // url to open as string

    private SubredditFragment newFragment; // the current foremost fragment if there is one



    public RedditFragment() {

        titleRedditSubreddits = new ArrayList<String>();
        urlRedditSubreddits = new ArrayList<String>();
        prefixRedditSubreddits = new ArrayList<String>();
        permalinkRedditSubreddits = new ArrayList<String>();
    }


    /**
     * This method u=is ide to start a new subredit
     */
    public void openSubreddit() {
        newFragment.openSubreddit();
    }

    /**
     * getter for the current context permalink
     * @return String the current permalink
     */
    public String getCurrentFragPL(){
        return this.currentFragmentPermalink;
    }

    /**
     * getter for the current context
     * @return SubredditFragment the fragment to reference
     */
    public SubredditFragment getNewFragment(){
        return newFragment;
    }

    /**
     * getter for the current context
     * @return SubredditFragment the fragment to reference
     */
    public SubredditFragment getCurrentContenxt(){
        return newFragment;
    }

    /**
     * @return String fro the webUrl to open for the content that is currently selected
     */
    public String sharePosting() {
        return "https://www.reddit.com" + currentFragmentPermalink;
    }



    public void initialSearchRequest(String searchTerm) {
        this.RedditURLTerm = searchTerm;
        titleRedditSubreddits = new ArrayList<String>();
        urlRedditSubreddits = new ArrayList<String>();
        prefixRedditSubreddits = new ArrayList<String>();
        permalinkRedditSubreddits = new ArrayList<String>();
        new DownloadRedditPosts().execute();
    }

    /**
     * This method will reset the arrayList items to new refernces as they will need to be
     * populated with their own specific information
     *
     * @param searchTerm String to the new search term that was selected
     */
    public void newSearchRequest(String searchTerm) {
        this.RedditURLTerm = searchTerm;
        titleRedditSubreddits = new ArrayList<String>();
        urlRedditSubreddits = new ArrayList<String>();
        prefixRedditSubreddits = new ArrayList<String>();
        permalinkRedditSubreddits = new ArrayList<String>();
        articleArrayAdapter = new ArrayAdapter<String>(containerActivity, R.layout.layout_resource_file, R.id.redditTextView, titleRedditSubreddits);
        if (redditListView != null){
            redditListView.setAdapter(articleArrayAdapter);
        }
        new DownloadRedditPosts().execute();


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
     *
     * This class will be used to querey a a new search term with results to populate
     * the ListView in UI with new data
     */
    public class DownloadRedditPosts extends AsyncTask<Object, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Object[] objects) {
            try {
                String json = "" ;
                URL url = new URL(RedditURL+RedditURLTerm+endURL);
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    String hold;
                    while ((hold = in.readLine()) != null){
                        json += hold;
                    }
                    JSONObject jsonObject = new JSONObject(json);
                    in.close();
                    return jsonObject;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) { e.printStackTrace(); }
            return null;
        }

        /**
         * This method will be used to mine data from the url that was sent to reddit to recieve
         * access to public posts in order to process requests
         * @param redditJSON JSONObject from the api request sent
         */
        @Override
        protected void onPostExecute(JSONObject redditJSON) {
            try {
                JSONObject arrayPointer = redditJSON.getJSONObject("data");
                JSONArray articleArray =  arrayPointer.getJSONArray("children");
                for (int i = 0; i< articleArray.length();i++) {
                    JSONObject data = articleArray.getJSONObject(i).getJSONObject("data");
                    if (data.has("url")){
                        String title = "This post has no title.";
                        if (data.has("title")){
                            title = data.getString("title");
                        }
                        String subreddit_name_prefixed = "No prefix found.";
                        if (data.has("subreddit_name_prefixed")){
                            subreddit_name_prefixed = data.getString("subreddit_name_prefixed");
                        }
                        String permalink = "";
                        if (data.has("permalink")){
                            permalink = data.getString("permalink");
                        }
                        permalinkRedditSubreddits.add(permalink);
                        prefixRedditSubreddits.add(subreddit_name_prefixed);
                        titleRedditSubreddits.add("Post Title:  "+title+"\t\n"
                                            +"Subreddit of Posting:  "+prefixRedditSubreddits.get(i));
                        urlRedditSubreddits.add(data.getString("url"));
                    }
                }

                articleArrayAdapter.notifyDataSetChanged();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Override method used to set the layout and attach the arrayAdapter to the listView and return
     * the view that we created
     * When this fragment is created this method will set the listview clickListener to on in order
     * to see which button was pushed and appropriatley grab the coresponding content to that
     * specific button
     * @param inflater LayoutInflater used to inflate the layout
     * @param container ViewGroup of where the view is contained
     * @param savedInstanceState Bundle of the instance state
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.fragment_reddit, container, false);
        redditListView = v.findViewById(R.id.reddit_post_list);
        setHasOptionsMenu(true);
        try {
            //new DownloadRedditPosts().execute();
            articleArrayAdapter = new ArrayAdapter<String>(containerActivity, R.layout.layout_resource_file,
                    R.id.redditTextView, titleRedditSubreddits);
            redditListView.setAdapter(articleArrayAdapter);
            redditListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    currentFragmentPermalink = permalinkRedditSubreddits.get(position);
                    currentFragmentSubreddit = prefixRedditSubreddits.get(position);
                    urlToOpen = "https://www.reddit.com"+currentFragmentPermalink;

                    newFragment = new SubredditFragment(urlToOpen, currentFragmentSubreddit);
                    newFragment.setContainterActivity(containerActivity);
                    newFragment.setTitle(titleRedditSubreddits.get(position));
                    FragmentTransaction fragTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragTransaction.replace(R.id.main_inner, newFragment);
                    fragTransaction.addToBackStack(null);
                    fragTransaction.commit();
                }
            });
            EditText et = (EditText) v.findViewById(R.id.reddit_field);
            et.setText(MainActivity.search);


            return v;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * This method will be used to add our menu bar to this fragment
     * @param menu our menu item
     * @param inflater the inflater to attach this to
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_menu, menu);
        return;
    }

}
