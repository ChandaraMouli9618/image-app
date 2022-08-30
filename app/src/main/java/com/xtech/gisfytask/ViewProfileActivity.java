package com.xtech.gisfytask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class ViewProfileActivity extends AppCompatActivity {

    private TextView name, className;
    private ImageView imgView;
    private VideoView vidView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        Profile profile = getIntent().getParcelableExtra("profile");

        name = findViewById(R.id.nameView_profileActivity);
        className = findViewById(R.id.classView_profileActivity);

        name.setText(profile.getName());
        className.setText(profile.getClassName());

        imgView = findViewById(R.id.imgView_profileView);
        imgView.setImageURI(Uri.parse(profile.getImgUri()));

        //Log.e("Content Provider",Uri.parse(profile.getVidUri()).getPath());

        //File file = new File(Uri.parse(profile.getVidUri()).getPath());
        //final String filepath = file.getAbsolutePath();

        vidView = findViewById(R.id.vidView_profileView);
        vidView.setVideoPath(profile.getVidUri());
        MediaController mc = new MediaController(getApplicationContext());
        mc.setAnchorView(vidView);
        vidView.setMediaController(mc);
        vidView.requestFocus();
        vidView.start();
    }
}
