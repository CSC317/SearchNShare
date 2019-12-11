package com.example.searchnshare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.graphics.drawable.BitmapDrawable;

import android.media.Image;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String search = "";
    public MenuFragment menuFrag;
    public MemeFragment memeFrag;
    public RedditFragment redditFrag;
    public FlickrFragment flickrFrag;
    public NewsFragment newsFrag;
    public AllFragment allFrag = new AllFragment();
    public FavoritesFragment favFrag;

    private EditText searchEdit; // EditText of the search Term
    private String searchText; // String reference to the search term
    private WebView myWebView;

    private ArrayList<FavoriteListItem> ourFavorites;

    String currentPhotoPath = null;
    String contactEmail = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ourFavorites = new ArrayList<>();
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
            searchText = search;
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
        else if (item.getItemId() == R.id.all_item) {
//            AllFragment frag = new AllFragment();
//            allFrag = frag;
            allFrag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, allFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (item.getItemId() == R.id.favorites_item) {
            FavoritesFragment frag = new FavoritesFragment();
            favFrag = frag;
            favFrag.setFavs(ourFavorites);
            favFrag.setAdapter(new FavoritesAdapter(this, R.layout.favorite_row, ourFavorites));
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

    public String redditSearch(){
        searchEdit = findViewById(R.id.reddit_field);
        searchText = searchEdit.getText().toString();
        if (redditFrag == null)
            return "";
        if (searchText.contains(" ")) {
            String[] searchTerms = searchText.split(" ");
            searchText = "";
            for (int i = 0; i<searchTerms.length;i++){
                searchText += searchTerms[i];
                searchText += "+";
            }
        }
        return searchText;
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

//    public void redditWebViewOpen(View v){
//        redditFrag.loadWebView();
//    }

    public void showSearch(View view) {
        EditText textField = (EditText) findViewById(R.id.text_field);
        RadioButton Meme = (RadioButton) findViewById(R.id.Meme);
        RadioButton Reddit = (RadioButton) findViewById(R.id.Reddit);
        RadioButton Flickr = (RadioButton) findViewById(R.id.Flickr);
        search = textField.getText().toString();

        if (search.equals("")){
            search = "java";
        }
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
        else {
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
            search = field.getText().toString();;
            memeFrag.showFragSearch();
        }
        else if (v.getId() == R.id.search_reddit) {
            EditText field = findViewById(R.id.reddit_field);
            search = field.getText().toString();
            searchText = search;
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

    public void redditFavorites(View v) {
        SubredditFragment reference = redditFrag.getCurrentContenxt();
        String title = reference.getTitle();
        String resource = "Reddit";
        FavoriteListItem newFav = new FavoriteListItem(title, null, resource);
        newFav.setURL(redditFrag.sharePosting());
        ourFavorites.add(newFav);

    }


    public void NewsRowClick(View v) {
        LinearLayout LL = (LinearLayout) v;
        TextView contentView = v.findViewById(R.id.news_row_content);
        TextView websiteView = v.findViewById(R.id.news_row_website);
        TextView urlView = v.findViewById(R.id.news_row_url);

        String content = contentView.getText().toString();
        String website = websiteView.getText().toString();
        String url = urlView.getText().toString();
        ShareNewsFragment frag = new ShareNewsFragment();
        frag.setWebsite(website);
        frag.setContentStr(content);
        frag.setNewsUrl(url);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        frag.setContainerActivity(this);
        transaction.replace(R.id.main_inner, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void FlickrRowClick(View v) {
        LinearLayout LL = (LinearLayout) v;
        TextView titleView = v.findViewById(R.id.flickr_row_title);
        ImageView imageView = v.findViewById(R.id.flickr_row_image);
        TextView urlView = v.findViewById(R.id.flickr_row_url);

        String title = titleView.getText().toString();
        Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        String url = urlView.getText().toString();
        ShareFlickrFragment frag = new ShareFlickrFragment();
        frag.setTitle(title);
        frag.setImage(image);
        frag.setFlickrUrl(url);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        frag.setContainerActivity(this);
        transaction.replace(R.id.main_inner, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    //Creates the image file of the screenshot taken of the drawing, this function was taken from
    // CollageCreator assignment.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public File createImageFileToSend(Bitmap bitmap) {
        File file = null;
        try {
            file = createImageFile();
        } catch (IOException ex) {
        }
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    public void onInfoClick(View v) {
        String text = ((TextView) v).getText().toString();
        String id = text.substring(text.indexOf(" :: ") + 4);
        String name = text.substring(text.indexOf(" || ") + 4, text.indexOf(" :: "));
        Cursor emails = memeFrag.containerActivity.getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id, null, null);

        if (emails.moveToNext()) {
            String email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
            contactEmail = email; // string representing the email to send the drawing to
        }
        emails.close();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("vnd.android.cursor.dir/email");

        intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{contactEmail});

        Uri uri = FileProvider.getUriForFile(this,
                BuildConfig.APPLICATION_ID + ".fileprovider", new File(currentPhotoPath));
        intent.putExtra(android.content.Intent.EXTRA_STREAM, uri);

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(intent);
    }

    //Creates the image file path of the screenshot taken of the drawing. This function was taken
    // from CollageCreator assignment
    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    public void shareMeme(View v){
        ShareMemeFragment shareMemeFrag = memeFrag.shareMemeFrag;
        ImageView memeView = memeFrag.memeClickedImageView;
        Bitmap bitmap = Bitmap.createBitmap(
                memeView.getWidth(), memeView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        memeView.draw(canvas);
        ContactsFragment cf = new ContactsFragment(memeFrag.fullUrl);
        cf.setContainerActivity(shareMemeFrag.getActivity());
        cf.sketcherFile = createImageFileToSend(bitmap);
        FragmentTransaction transaction = shareMemeFrag.getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_inner, cf);
        transaction.addToBackStack(null);
        transaction.commit();

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
