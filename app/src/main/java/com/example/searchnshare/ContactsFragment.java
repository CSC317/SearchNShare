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

public class ContactsFragment extends Fragment {


    private Activity containerActivity = null;
    private View inflatedView = null;
    public File sketcherFile = null;

    private ListView contactsListView;
    ArrayAdapter<String> contactsAdapter = null;
    private ArrayList<String> contacts = new ArrayList<String>();
    public String url;

    public ContactsFragment(String url) {
        this.url = url;
    }

    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_contacts, container, false);
        return inflatedView;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        getContacts();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupContactsAdapter();
    }

    // Adds a string representing a contact to an ArrayList of Strings that will be used to display
    // in an adapter when the user clicks the share button
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


    // sets an adapter of containing all contacts to a ListView in order to display the user's contacts.
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


