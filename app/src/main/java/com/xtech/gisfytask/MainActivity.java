package com.xtech.gisfytask;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 100;

    private Button addBtn;
    private Button viewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addBtn = findViewById(R.id.addBtn);
        viewBtn = findViewById(R.id.viewBtn);

        addBtn.setOnClickListener(view -> {
            Intent profileIntent = new Intent(getApplicationContext(), AddProfileActivity.class);
            startActivity(profileIntent);
        });

        viewBtn.setOnClickListener(view -> {
           if( !checkPermission() ) requestPermission();
            Intent viewIntent = new Intent(getApplicationContext(), ViewActivity.class);
            startActivity(viewIntent);
        });
    }

    private void requestPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if(!Environment.isExternalStorageManager()){
                try{
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                    startActivityIfNeeded(intent, 101);
                }catch(Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    startActivityIfNeeded(intent, 101);
                }
            }
        }
    }
//
//    private ActivityResultLauncher<Intent> storageActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
//
//                    }
//                }
//            }
//    );



    public boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }else{
//            int write = ContextCompat.checkSelfPermission(this,  Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            int read = ContextCompat.checkSelfPermission(this,  Manifest.permission.READ_EXTERNAL_STORAGE);

            int permission = ContextCompat.checkSelfPermission(this,  Manifest.permission.WRITE_EXTERNAL_STORAGE);

//            return write == PackageManager.PERMISSION_DENIED && read == PackageManager.PERMISSION_GRANTED;
            return permission == PackageManager.PERMISSION_GRANTED;
        }
    }




}