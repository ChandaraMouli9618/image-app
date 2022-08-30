package com.xtech.gisfytask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    private ArrayList<Profile> profileList;
    private DBHandler dbHandler;
    private ProfileRVAdapter profileRVAdapter;
    private RecyclerView profileRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        profileList = new ArrayList<>();
        dbHandler = new DBHandler(ViewActivity.this);
        profileList = dbHandler.readProfiles();

        profileRVAdapter = new ProfileRVAdapter(profileList, ViewActivity.this);
        profileRV = findViewById(R.id.profileRV);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        profileRV.setLayoutManager(linearLayoutManager);

        profileRV.setAdapter(profileRVAdapter);
    }
}