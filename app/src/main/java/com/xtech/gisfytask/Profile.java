package com.xtech.gisfytask;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {
    public String name;

    protected Profile(Parcel in) {
        name = in.readString();
        className = in.readString();
        imgUri = in.readString();
        vidUri = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(className);
        parcel.writeString(imgUri);
        parcel.writeString(vidUri);
    }

    private String className;
    private String imgUri;
    private String vidUri;

    public Profile(String name, String className, String imgUri, String vidUri) {
        this.name = name;
        this.className = className;
        this.imgUri = imgUri;
        this.vidUri = vidUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getVidUri() {
        return vidUri;
    }

    public void setVidUri(String vidUri) {
        this.vidUri = vidUri;
    }
}
