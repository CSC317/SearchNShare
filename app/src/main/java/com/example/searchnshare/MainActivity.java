package com.example.searchnshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MenuFragment frag = new MenuFragment();
        frag.setContainerActivity(this);
        transaction.replace(R.id.inner, frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
