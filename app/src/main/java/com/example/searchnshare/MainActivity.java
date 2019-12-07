package com.example.searchnshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    public static String search = "";
    public MenuFragment menuFrag;
    public MemeFragment memeFrag;
    public RedditFragment redditFrag;
    public FlickrFragment flickrFrag;
    public NewsFragment newsFrag;
    public FavoritesFragment favFrag;

    private EditText searchEdit; // EditText of the search Term
    private String searchText; // String reference to the search term
    private WebView myWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MenuFragment frag = new MenuFragment();
        menuFrag = frag;
        frag.setContainerActivity(this);
        transaction.replace(R.id.main_inner, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (item.getItemId() == R.id.meme_item) {
            MemeFragment frag = new MemeFragment();
            memeFrag = frag;
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (item.getItemId() == R.id.reddit_item) {
            RedditFragment frag = new RedditFragment();
            redditFrag = frag;
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (item.getItemId() == R.id.flickr_item) {
            FlickrFragment frag = new FlickrFragment();
            flickrFrag = frag;
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (item.getItemId() == R.id.news_item) {
            NewsFragment frag = new NewsFragment();
            newsFrag = frag;
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (item.getItemId() == R.id.favorites_item) {
            FavoritesFragment frag = new FavoritesFragment();
            favFrag = frag;
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else {
            MenuFragment frag = new MenuFragment();
            menuFrag = frag;
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        RadioButton Meme = (RadioButton) findViewById(R.id.Meme);
        RadioButton Reddit = (RadioButton) findViewById(R.id.Reddit);
        RadioButton Flickr = (RadioButton) findViewById(R.id.Flickr);
        RadioButton News = (RadioButton) findViewById(R.id.News);
        // Check which radio button was clicked
        if (view.getId() == R.id.Meme && checked) {
            Reddit.setChecked(false);
            Flickr.setChecked(false);
            News.setChecked(false);
        }
        if (view.getId() == R.id.Reddit && checked) {
            Meme.setChecked(false);
            Flickr.setChecked(false);
            News.setChecked(false);
        }
        if (view.getId() == R.id.Flickr && checked) {
            Reddit.setChecked(false);
            Meme.setChecked(false);
            News.setChecked(false);
        }
        if (view.getId() == R.id.News && checked) {
            Reddit.setChecked(false);
            Flickr.setChecked(false);
            Meme.setChecked(false);
        }
    }

    public void redditSearch(View v){
        searchEdit = findViewById(R.id.reddit_field);
        searchText = searchEdit.getText().toString();
        if (redditFrag == null)
            return;
        if (searchText.contains(" ")) {
            String[] searchTerms = searchText.split(" ");
            searchText = "";
            for (int i = 0; i<searchTerms.length;i++){
                searchText += searchTerms[i];
                searchText += "+";
            }
        }
        redditFrag.newSearchRequest(searchText);
    }

    @Override
    public void onBackPressed() {
        myWebView = findViewById(R.id.webView);
        if (myWebView == null)
            super.onBackPressed();
        else if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void openSubreddit(View v){
        redditFrag.openSubreddit();
    }

    public void redditWebViewOpen(View v){
        redditFrag.loadWebView();
    }

    public void showSearch(View view) {
        EditText textField = (EditText) findViewById(R.id.text_field);
        RadioButton Meme = (RadioButton) findViewById(R.id.Meme);
        RadioButton Reddit = (RadioButton) findViewById(R.id.Reddit);
        RadioButton Flickr = (RadioButton) findViewById(R.id.Flickr);
        search = textField.getText().toString();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (Meme.isChecked()) {
            MemeFragment frag = new MemeFragment();
            memeFrag = frag;
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (Reddit.isChecked()) {
            RedditFragment frag = new RedditFragment();
            redditFrag = frag;
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
            searchText = search;
            if (searchText.contains(" ")) {
                String[] searchTerms = searchText.split(" ");
                searchText = "";
                for (int i = 0; i<searchTerms.length;i++){
                    searchText += searchTerms[i];
                    searchText += "+";
                }
            }
            redditFrag.initialSearchRequest(searchText);
        }
        else if (Flickr.isChecked()) {
            FlickrFragment frag = new FlickrFragment();
            flickrFrag = frag;
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else  {
            NewsFragment frag = new NewsFragment();
            newsFrag = frag;
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    public void showFragSearch(View v) {
        if (v.getId() == R.id.search_meme) {
            EditText field = findViewById(R.id.meme_field);
            search = field.getText().toString();
            String Do = "RUN TWITTER ASYNC TASK";
            memeFrag.showFragSearch();
        }
        else if (v.getId() == R.id.search_reddit) {
            EditText field = findViewById(R.id.reddit_field);
            search = field.getText().toString();
            redditFrag.newSearchRequest(search);
        }
        else if (v.getId() == R.id.search_flickr) {
            EditText field = findViewById(R.id.flickr_field);
            search = field.getText().toString();
            flickrFrag.showFragSearch();
        }
        else if (v.getId() == R.id.search_news) {
            EditText field = findViewById(R.id.news_field);
            search = field.getText().toString();
            newsFrag.showFragSearch();
        }
    }

//    public void nextFragment(View v) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if (v.getId() == R.id.TForwardButton) {
//            RedditFragment frag = new RedditFragment();
//            frag.setContainerActivity(this);
//            transaction.replace(R.id.main_inner, frag);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//        else if (v.getId() == R.id.RForwardButton) {
//            FlickrFragment frag = new FlickrFragment();
//            frag.setContainerActivity(this);
//            transaction.replace(R.id.main_inner, frag);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//        else if (v.getId() == R.id.FForwardButton) {
//            NewsFragment frag = new NewsFragment();
//            frag.setContainerActivity(this);
//            transaction.replace(R.id.main_inner, frag);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//        else if (v.getId() == R.id.NForwardButton) {
//            FavoritesFragment frag = new FavoritesFragment();
//            frag.setContainerActivity(this);
//            transaction.replace(R.id.main_inner, frag);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//        // Favorites forward button
//        else {
//            MenuFragment frag = new MenuFragment();
//            frag.setContainerActivity(this);
//            transaction.replace(R.id.main_inner, frag);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//    }
//
//    public void previousFragment(View v) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if (v.getId() == R.id.TBackButton) {
//            MenuFragment frag = new MenuFragment();
//            frag.setContainerActivity(this);
//            transaction.replace(R.id.main_inner, frag);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//        else if (v.getId() == R.id.RBackButton) {
//            TwitterFragment frag = new TwitterFragment();
//            frag.setContainerActivity(this);
//            transaction.replace(R.id.main_inner, frag);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//        else if (v.getId() == R.id.FBackButton) {
//            RedditFragment frag = new RedditFragment();
//            frag.setContainerActivity(this);
//            transaction.replace(R.id.main_inner, frag);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//        else if (v.getId() == R.id.NBackButton) {
//            FlickrFragment frag = new FlickrFragment();
//            frag.setContainerActivity(this);
//            transaction.replace(R.id.main_inner, frag);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//        // Favorites back button
//        else {
//            NewsFragment frag = new NewsFragment();
//            frag.setContainerActivity(this);
//            transaction.replace(R.id.main_inner, frag);
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//    }
}
