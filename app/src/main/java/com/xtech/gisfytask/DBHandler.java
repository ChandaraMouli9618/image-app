package com.xtech.gisfytask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(Context context){
        super(context, DB_NAME, null, 1);
    }

    private static final String DB_NAME = "gisfy";
    private static final String TABLE_NAME = "profile";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String CLASS_COL = "class";
    private static final String IMG_URI_COL = "img";
    private static final String VID_URI_COL = "vid";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query_createDB = "CREATE TABLE " + TABLE_NAME +
                " (" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME_COL + " TEXT, " +
                CLASS_COL + " TEXT, " +
                IMG_URI_COL + " TEXT, " +
                VID_URI_COL + " TEXT)";
        sqLiteDatabase.execSQL(query_createDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    public void addNewProfile(String name, String className, String imgUri, String vidUri) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues profileData = new ContentValues();

        profileData.put(NAME_COL, name);
        profileData.put(CLASS_COL, className);
        profileData.put(IMG_URI_COL, imgUri);
        profileData.put(VID_URI_COL, vidUri);

        db.insert(TABLE_NAME, null, profileData);

        db.close();
    }

    public ArrayList<Profile> readProfiles() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor profileCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<Profile> profileList = new ArrayList<>();

        if (profileCursor.moveToFirst()) {
            do {
                profileList.add(new Profile(
                        profileCursor.getString(1),
                        profileCursor.getString(2),
                        profileCursor.getString(3),
                        profileCursor.getString(4)));
            } while (profileCursor.moveToNext());
        }

        profileCursor.close();
        return profileList;
    }
}
