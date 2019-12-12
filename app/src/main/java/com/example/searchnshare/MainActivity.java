package com.example.searchnshare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.util.DisplayMetrics;
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

/**
 * This class is the main activity for our SearchNShare Application. It will save the most recent
 * search term at all times.
 */
public class MainActivity extends AppCompatActivity {

    public static String search = "";
    public MenuFragment menuFrag;
    public MemeFragment memeFrag;
    public RedditFragment redditFrag;
    public FlickrFragment flickrFrag;
    public ShareFlickrFragment shareFlickrFrag;
    public NewsFragment newsFrag;
    public ShareNewsFragment shareNewsFrag;
    public AllFragment allFrag = new AllFragment();
    public AllSelectedFragment allSelectedFragment;
    public FavoritesFragment favFrag;
    public FavoriteSelectedFragment favSelectedFragment;

    private EditText searchEdit; // EditText of the search Term
    private String searchText; // String reference to the search term
    private WebView myWebView;

    private ArrayList<FavoriteListItem> ourFavorites;
    private int x;

    String currentPhotoPath ;
    String contactEmail = "";

    /**
     * This method will call when the Main Activity is crated. It will check if the screen size is
     * normal or a tablet an set the menuFragment to replace specific layouts. The Menu Fragment is
     * then brought into the foreground.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBigScreen()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setContentView(R.layout.activity_main_big);
        }
        else {
            setContentView(R.layout.activity_main);
        }
        ourFavorites = new ArrayList<>();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MenuFragment frag = new MenuFragment();
        menuFrag = frag;
        frag.setContainerActivity(this);
        if (isBigScreen()) {
            transaction.replace(R.id.outer, frag);
        }
        else {
            transaction.replace(R.id.main_inner, frag);
        }
        //transaction.replace(R.id.main_inner, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * @return true if the screen is larger than 5 by 5 inches, fale otherwise
     */
    private boolean isBigScreen() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float xInches = (((float)metrics.widthPixels) / metrics.xdpi);
        float yInches = (((float)metrics.widthPixels) / metrics.xdpi);
        return xInches > 5.0 && yInches > 5.0;
    }

    /**
     * Sets the ALL Selected Fragment to the parameter frag.
     *
     * @param frag is an AllSelectedFragment.
     */
    public void setAllSelectedFragment(AllSelectedFragment frag) {
        this.allSelectedFragment = frag;
    }

    /**
     * Sets the Favorite Selected Fragment to the parameter frag.
     *
     * @param frag is an FavoriteSelectedFragment.
     */
    public void setFavSelectedFragment(FavoriteSelectedFragment frag) {
        this.favSelectedFragment = frag;
    }

    /**
     * This method is called when a menu item is selected from any menu. Depending on which item was
     * clicked, a fragment transaction will bring the selected API fragment into the foreground.
     *
     * @param item is a MenuItem.
     * @return
     */
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
        else if (item.getItemId() == R.id.help_item) {
            HelpFragment frag = new HelpFragment();
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else {
            MenuFragment frag = new MenuFragment();
            menuFrag = frag;
            frag.setContainerActivity(this);
            if (isBigScreen()) {
                transaction.replace(R.id.outer, frag);
            }
            else {
                transaction.replace(R.id.main_inner, frag);
            }
            transaction.addToBackStack(null);
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when a Radio Button is clicked from the main Menu. It will check the one
     * the user selected and uncheck all of the others.
     *
     * @param view is the radio button view that was clicked.
     */
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        RadioButton Meme = (RadioButton) findViewById(R.id.Meme);
        RadioButton Reddit = (RadioButton) findViewById(R.id.Reddit);
        RadioButton Flickr = (RadioButton) findViewById(R.id.Flickr);
        RadioButton News = (RadioButton) findViewById(R.id.News);
        RadioButton all = (RadioButton) findViewById(R.id.ALL);
        if (view.getId() == R.id.Meme && checked) {
            Reddit.setChecked(false);
            Flickr.setChecked(false);
            News.setChecked(false);
            all.setChecked(false);
        }
        if (view.getId() == R.id.Reddit && checked) {
            Meme.setChecked(false);
            Flickr.setChecked(false);
            News.setChecked(false);
            all.setChecked(false);
        }
        if (view.getId() == R.id.Flickr && checked) {
            Reddit.setChecked(false);
            Meme.setChecked(false);
            News.setChecked(false);
            all.setChecked(false);
        }
        if (view.getId() == R.id.News && checked) {
            Reddit.setChecked(false);
            Flickr.setChecked(false);
            Meme.setChecked(false);
            all.setChecked(false);
        }
        if (view.getId() == R.id.ALL && checked) {
            Reddit.setChecked(false);
            Flickr.setChecked(false);
            Meme.setChecked(false);
            News.setChecked(false);
        }
    }

    /**
     * This method is called when the back button is pressed while in the fragment of a selected
     * Reddit item. This method ensures the user will go back to the fragment after opening the
     * subreddit website and have the previous post in the webview.
     */
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

    /**
     * This method calls a method in the Reddit fragment to open the current Subreddit.
     *
     * @param v is the view of the button clicked.
     */
    public void openSubreddit(View v){
        redditFrag.openSubreddit();
    }

    /**
     * This method opens an API fragment depending on which radio button is selected on the main menu.
     * The search term is automatically searched when entering the new fragment.
     *
     * @param view is the view of the "search with" button clicked.
     */
    public void showSearch(View view) {
        EditText textField = (EditText) findViewById(R.id.text_field);
        RadioButton Meme = (RadioButton) findViewById(R.id.Meme);
        RadioButton Reddit = (RadioButton) findViewById(R.id.Reddit);
        RadioButton Flickr = (RadioButton) findViewById(R.id.Flickr);
        RadioButton News = (RadioButton) findViewById(R.id.News);
        search = textField.getText().toString();

        if (search.equals("")){
            search = "news";
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
        else if (News.isChecked()) {
            NewsFragment frag = new NewsFragment();
            newsFrag = frag;
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else {
            AllFragment frag = new AllFragment();
            allFrag = frag;
            frag.setContainerActivity(this);
            transaction.replace(R.id.main_inner, frag);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    /**
     * This method shows the individual fragment search depending on the fragment in which the user
     * searched a new term.
     *
     * @param v is the view of a button clicked in one of the 4 APIs or the ALL fragment.
     */
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
        else if (v.getId() == R.id.search_all) {
            EditText field = findViewById(R.id.all_field);
            search = field.getText().toString();
            allFrag.showFragSearch();
        }
    }

    /**
     * This method adds the current reddit post to the favorites page.
     *
     * @param v is the view of the "favorites" button in the subreddit fragment.
     */
    public void redditFavorites(View v) {
        SubredditFragment reference = redditFrag.getCurrentContenxt();
        String title = reference.getTitle();
        String resource = "Reddit";
        FavoriteListItem newFav = new FavoriteListItem(title, null, resource);
        newFav.setURL(redditFrag.sharePosting());
        ourFavorites.add(newFav);

    }

    /**
     * This method adds the current flickr post to the favorites page.
     *
     * @param v is the view of the "add to favorites" button in the shareFlickrfragment.
     */
    public void flickrFavorites(View v) {
        String title = shareFlickrFrag.getTitle();
        Bitmap image = shareFlickrFrag.getImage();
        String resource = "flickr";
        FavoriteListItem newFav = new FavoriteListItem(title, image, resource);
        newFav.setURL(shareFlickrFrag.getFlickrUrl());
        ourFavorites.add(newFav);

    }

    /**
     * This method adds the current news post to the favorites page.
     *
     * @param v is the view of the "add to favorites" button in the share news fragment.
     */
    public void newsFavorites(View v) {
        String website = shareNewsFrag.getWebsite();
        String content = shareNewsFrag.getContentStr();
        String resource = "news";
        FavoriteListItem newFav = new FavoriteListItem(website + "\n" + content, null, resource);
        newFav.setURL(shareNewsFrag.getNewsUrl());
        ourFavorites.add(newFav);

    }

    /**
     * This method adds the current post to the favorites page.
     *
     * @param v is the view of the "add to favorites" button in the all selected fragment.
     */
    public void ALLFavorites(View v) {
        String title = allSelectedFragment.getAnyTitle();
        Bitmap image = allSelectedFragment.getAnyImage();

        String resource = "all";
        FavoriteListItem newFav = new FavoriteListItem(title, image, resource);
        newFav.setURL(allSelectedFragment.getAnyUrl());
        ourFavorites.add(newFav);
    }

    /**
     * This method adds the current meme post to the favorites page.
     *
     * @param v is the view of the "add to favorites" button in the meme fragment.
     */
    public void memeFavorites(View v) {
        String title = memeFrag.getMemeClickedtitle();
        String resource = "Meme";
        Bitmap memeBitmap = memeFrag.getMemeBitmap();
        FavoriteListItem memeFav = new FavoriteListItem(title, memeBitmap, resource);
        memeFav.setURL(memeFrag.getMemeWebUrl());
        ourFavorites.add(memeFav);

    }

    /**
     * This method is called when a row in the News Fragment is clicked. It will open a new
     * ShareNewsFragment with the current post information.
     *
     * @param v is the view of the news row clicked.
     */
    public void NewsRowClick(View v) {
        LinearLayout LL = (LinearLayout) v;
        TextView contentView = v.findViewById(R.id.news_row_content);
        TextView websiteView = v.findViewById(R.id.news_row_website);
        TextView urlView = v.findViewById(R.id.news_row_url);

        String content = contentView.getText().toString();
        String website = websiteView.getText().toString();
        String url = urlView.getText().toString();
        ShareNewsFragment frag = new ShareNewsFragment();
        shareNewsFrag = frag;
        frag.setWebsite(website);
        frag.setContentStr(content);
        frag.setNewsUrl(url);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        frag.setContainerActivity(this);
        transaction.replace(R.id.main_inner, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * This method is called when a row in the Flickr Fragment is clicked. It will open a new
     * ShareFlickrFragment with the current post information.
     *
     * @param v is the view of the flickr row clicked.
     */
    public void FlickrRowClick(View v) {
        LinearLayout LL = (LinearLayout) v;
        TextView titleView = v.findViewById(R.id.flickr_row_title);
        ImageView imageView = v.findViewById(R.id.flickr_row_image);
        TextView urlView = v.findViewById(R.id.flickr_row_url);

        String title = titleView.getText().toString();
        Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        String url = urlView.getText().toString();
        ShareFlickrFragment frag = new ShareFlickrFragment();
        shareFlickrFrag = frag;
        frag.setTitle(title);
        frag.setImage(image);
        frag.setFlickrUrl(url);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        frag.setContainerActivity(this);
        transaction.replace(R.id.main_inner, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    /**
     * Creates the image file of the screenshot taken of the drawing, this function was taken from
     * CollageCreator assignment.
     *
     * @param bitmap
     * @return
     */
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

    /**
     * This method is called when a row in the Contact Fragment is clicked. It will get the url of the
     * current post and open an email with the contact's email as the receiver and the current url
     * as an attachment.
     *
     * @param v is the view of the contact row clicked.
     */
    public void onInfoClick(View v) {
        String text = ((TextView) v).getText().toString();
        String id = text.substring(text.indexOf(" :: ") + 4);
        String name = text.substring(text.indexOf(" || ") + 4, text.indexOf(" :: "));
        Cursor emails = null;

        if (x == 0) {
            emails = memeFrag.getContainerActivity().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id, null, null);
        } else if (x == 1) {
            emails = redditFrag.getNewFragment().getContainterActivity().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id, null, null);
        } else if (x == 2) {
            emails = shareFlickrFrag.getActivity().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id, null, null);
        } else if (x == 3) {
            emails = shareNewsFrag.getActivity().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id, null, null);
        } else if (x == 4) {

            emails = allSelectedFragment.getActivity().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id, null, null);
        } else if (x == 5) {


            emails = favSelectedFragment.getActivity().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id, null, null);

        }
            if (emails.moveToNext()) {
                String email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                contactEmail = email; // string representing the email to send the drawing to
            }
            emails.close();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("vnd.android.cursor.dir/email");

            intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{contactEmail});

            if (x == 0) {
                Uri uri = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".fileprovider", new File(currentPhotoPath));
                intent.putExtra(android.content.Intent.EXTRA_STREAM, uri);
            } else if (x == 1) {
                intent.putExtra(Intent.EXTRA_TEXT, redditFrag.getNewFragment().getUrlToOpen());
            } else if (x == 2) {
                intent.putExtra(Intent.EXTRA_TEXT, shareFlickrFrag.getFlickrUrl());
            } else if (x == 3) {
                intent.putExtra(Intent.EXTRA_TEXT, shareNewsFrag.getNewsUrl());
            } else if (x == 4) {
                intent.putExtra(Intent.EXTRA_TEXT, allSelectedFragment.getAnyUrl());
            } else if (x == 5) {
                intent.putExtra(Intent.EXTRA_TEXT, favSelectedFragment.getURL());
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(intent);
        }


    /**
     * Creates the image file path of the screenshot taken of the drawing. This function was taken
     * from CollageCreator assignment
     *
     * @return
     * @throws IOException
     */
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

    /**
     * This method is called when the "share" button in the ShareMemeFragment is clicked and it opens
     * the contact fragment to select the contact to send a link to.
     *
     * @param v is the view of the "share" button.
     */
    public void shareMeme(View v){
        x = 0;
        ShareMemeFragment shareMemeFrag = memeFrag.getShareMemeFrag();
        ImageView memeView = memeFrag.getMemeClickedImageView();
        Bitmap bitmap = Bitmap.createBitmap(
                memeView.getWidth(), memeView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        memeView.draw(canvas);
        ContactsFragment cf = new ContactsFragment(memeFrag.getFullUrl());
        cf.setContainerActivity(shareMemeFrag.getActivity());
        cf.sketcherFile = createImageFileToSend(bitmap);
        FragmentTransaction transaction = shareMemeFrag.getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_inner, cf);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * This method is called when the "share" button in the SubredditFragment is clicked and it opens
     * the contact fragment to select the contact to send a link to.
     *
     * @param v is the view of the "share" button.
     */
    public void shareReddit(View v){
        x = 1;
        ContactsFragment cf = new ContactsFragment(redditFrag.getCurrentFragPL());
        cf.setContainerActivity(redditFrag.getNewFragment().getActivity());
        cf.url = redditFrag.getCurrentFragPL();
        FragmentTransaction trans = redditFrag.getNewFragment().getActivity().getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.main_inner, cf);
        trans.addToBackStack(null);
        trans.commit();
    }

    /**
     * This method is called when the "share" button in the ShareFlickrFragment is clicked and it opens
     * the contact fragment to select the contact to send a link to.
     *
     * @param v is the view of the "share" button.
     */
    public void shareFlickr(View v) {
        x = 2;
        ContactsFragment cf = new ContactsFragment(shareFlickrFrag.getFlickrUrl());
        cf.setContainerActivity(this);
        cf.url = shareFlickrFrag.getFlickrUrl();
        FragmentTransaction trans = shareFlickrFrag.getActivity().getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.main_inner, cf);
        trans.addToBackStack(null);
        trans.commit();
    }

    /**
     * This method is called when the "share" button in the ShareNewsFragment is clicked and it opens
     * the contact fragment to select the contact to send a link to.
     *
     * @param v is the view of the "share" button.
     */
    public void shareNews(View v) {
        x = 3;
        ContactsFragment cf = new ContactsFragment(shareNewsFrag.getNewsUrl());
        cf.setContainerActivity(this);
        cf.url = shareNewsFrag.getNewsUrl();
        FragmentTransaction trans = shareNewsFrag.getActivity().getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.main_inner, cf);
        trans.addToBackStack(null);
        trans.commit();
    }

    /**
     * This method is called when the "share" button in the AllSelectedFragment is clicked and it opens
     * the contact fragment to select the contact to send a link to.
     *
     * @param v is the view of the "share" button.
     */
    public void shareAll(View v) {
        x = 4;
        ContactsFragment cf = new ContactsFragment(allSelectedFragment.getAnyUrl());
        cf.setContainerActivity(this);
        cf.url = allSelectedFragment.getAnyUrl();
        FragmentTransaction trans = allSelectedFragment.getActivity().getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.main_inner, cf);
        trans.addToBackStack(null);
        trans.commit();
    }

    /**
     * This method is called when the "share" button in the FavoriteSelectedFragment is clicked and it opens
     * the contact fragment to select the contact to send a link to.
     *
     * @param v is the view of the "share" button.
     */
    public void shareFavs(View v) {
        x = 5;
        ContactsFragment cf = new ContactsFragment(favSelectedFragment.getURL());
        cf.setContainerActivity(this);
        cf.url = favSelectedFragment.getURL();
        FragmentTransaction trans = favSelectedFragment.getActivity().getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.main_inner, cf);
        trans.addToBackStack(null);
        trans.commit();
    }

}
