package com.example.searchnshare;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * This fragment is a listview of contacts from the device that, when clicked, will open the Gmail
 * app to send a link to the contact selected.
 */
public class ContactsFragment extends Fragment {


    private Activity containerActivity = null;
    private View inflatedView = null;
    public File sketcherFile = null;

    private ListView contactsListView;
    ArrayAdapter<String> contactsAdapter = null;
    private ArrayList<String> contacts = new ArrayList<String>();
    public String url;

    /**
     * This is the constructor of the ContactsFragment. It takes a url as a parameter to send after
     * selecting a contact.
     *
     * @param url is a String.
     */
    public ContactsFragment(String url) {
        this.url = url;
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
     * This method is called when the ContactsFragment comes into view. This method inflates the
     * fragment into the desired view and returns the inflated view.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the inflated view of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_contacts, container, false);
        return inflatedView;
    }

    /**
     * This method is called when the ContactsFragment is created. In this method, the getContacts()
     * method is called to fill the contacts ArrayList with contacts from the device.
     *
     * @param savedInstance
     */
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        getContacts();
    }

    /**
     * This method is called when the fragment is resumed, after leaving the view. In ths method,
     * the method SetupContactsAdapter() is called to setup the Adapter for this fragment.
     */
    @Override
    public void onResume() {
        super.onResume();
        setupContactsAdapter();
    }

    /**
     *  Adds a string representing a contact to an ArrayList of Strings that will be used to display
     *  in an adapter when the user clicks the share button.
     */
    public void getContacts() {
        int limit = 1000;
        Cursor cursor = containerActivity.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext() && limit > 0) {
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String given = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contact = "Contact || " + given + " :: " + contactId; // Shows contact name
            contacts.add(contact);
            limit--;
        }
        cursor.close();
    }


    /**
     * sets an adapter of containing all contacts to a ListView in order to display the user's contacts.
     */
    private void setupContactsAdapter() {
        String contactEmail;
        contactsListView =
                (ListView) containerActivity.findViewById(R.id.contact_list_view);
        contactsAdapter = new
                ArrayAdapter<String>(containerActivity, R.layout.contact_row,
                R.id.text_row_text_view, contacts);
        contactsListView.setAdapter(contactsAdapter);
    }

}


