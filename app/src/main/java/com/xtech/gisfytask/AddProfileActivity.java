package com.xtech.gisfytask;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class AddProfileActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etClass;
    private Button chooseVidBtn;
    private Button chooseImgBtn;
    private Button saveBtn;
    private TextView imgPathView;
    private TextView vidPathView;

    private DBHandler dbHandler;

    private Uri imgURI = null;
    private Uri vidURI = null;
    private String vidPath = "";

    private int imgURIRequestCode = 1;
    private int vidURIRequestCode = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etName =findViewById(R.id.etName);
        etClass =findViewById(R.id.etClass);
        chooseVidBtn = findViewById(R.id.chooseVideoBtn);
        chooseImgBtn = findViewById(R.id.chooseImgBtn);
        saveBtn = findViewById(R.id.saveBtn);
        imgPathView = findViewById(R.id.imgPath);
        vidPathView = findViewById(R.id.vidPath);

        dbHandler = new DBHandler(getApplicationContext());

        chooseImgBtn.setOnClickListener(view -> {
            getURI("image", imgURIRequestCode);
        });

        chooseVidBtn.setOnClickListener(view -> {
            getURI("video", vidURIRequestCode);
        });

        saveBtn.setOnClickListener(view->{

            String name = etName.getText().toString();
            String className = etClass.getText().toString();

            if(name.length() == 0) {
                shortToast("Name cannot be empty.");
                return;
            }
            else if(className.length() == 0) {
                shortToast("Class cannot be empty.");
                return;
            }
            else if(imgURI == null) {
                shortToast("Choose a Photo to continue.");
                return;
            }
            else if(vidURI == null) {
                shortToast("Choose a video to continue.");
                return;
            }

            //If all fields are valid.
            dbHandler.addNewProfile(name, className, imgURI.toString(), vidPath);
            shortToast("Data Added Successfully");

            etName.setText("");
            etClass.setText("");
            imgPathView.setText("Choose Image");
            vidPathView.setText("Choose Video");
            imgURI = vidURI = null;
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == imgURIRequestCode && resultCode == RESULT_OK) {
            imgURI = data.getData();
            //Fixed Path Column
            String[] fpc = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(imgURI,
                    fpc, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(fpc[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            String[] resource = picturePath.split("/");
            imgPathView.setText(resource[resource.length-1]);
        }
        else if (requestCode == vidURIRequestCode && resultCode == RESULT_OK) {
            vidURI = data.getData();
            String[] fpc = {MediaStore.Video.Media.DATA};
            Cursor cursor = getContentResolver().query(vidURI,
                    fpc, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(fpc[0]);
            String videoPath = cursor.getString(columnIndex);
            cursor.close();
            String[] resource = videoPath.split("/");
            vidPathView.setText(resource[resource.length-1]);
            vidPath = videoPath;
        }

    }

    void getURI(String type,int requestCode){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType( type + "/*");
        startActivityForResult(intent, requestCode);
    }

    void shortToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}