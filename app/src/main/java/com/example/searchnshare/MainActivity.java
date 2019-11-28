package com.example.searchnshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    public static String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MenuFragment frag = new MenuFragment();
        frag.setContainerActivity(this);
        transaction.replace(R.id.main_inner, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        RadioButton Twitter = (RadioButton) findViewById(R.id.Twitter);
        RadioButton Reddit = (RadioButton) findViewById(R.id.Reddit);
        RadioButton Flickr = (RadioButton) findViewById(R.id.Flickr);
        RadioButton News = (RadioButton) findViewById(R.id.News);
        // Check which radio button was clicked
        if (view.getId() == R.id.Twitter && checked) {
            Reddit.setChecked(false);
            Flickr.setChecked(false);
            News.setChecked(false);
        }
        if (view.getId() == R.id.Reddit && checked) {
            Twitter.setChecked(false);
            Flickr.setChecked(false);
            News.setChecked(false);
        }
        if (view.getId() == R.id.Flickr && checked) {
            Reddit.setChecked(false);
            Twitter.setChecked(false);
            News.setChecked(false);
        }
        if (view.getId() == R.id.News && checked) {
            Reddit.setChecked(false);
            Flickr.setChecked(false);
            Twitter.setChecked(false);
        }
    }

    public void showSearch(View view) {
        EditText textField = (EditText) findViewById(R.id.text_field);
        RadioButton Twitter = (RadioButton) findViewById(R.id.Twitter);
        RadioButton Reddit = (RadioButton) findViewById(R.id.Reddit);
        RadioButton Flickr = (RadioButton) findViewById(R.id.Flickr);
        search = textField.getText().toString();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (Twitter.isChecked()) {
            TwitterFragment frag = new TwitterFragment();
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (Reddit.isChecked()) {
            RedditFragment frag = new RedditFragment();
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (Flickr.isChecked()) {
            FlickrFragment frag = new FlickrFragment();
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else  {
            NewsFragment frag = new NewsFragment();
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}
