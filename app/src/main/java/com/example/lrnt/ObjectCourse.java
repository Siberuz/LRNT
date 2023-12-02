package com.example.lrnt;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ObjectCourse implements Parcelable {
    private String title;
    private String desc;

    public ObjectCourse(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    protected ObjectCourse(Parcel in) {
        title = in.readString();
        desc = in.readString();
    }

    public static final Creator<ObjectCourse> CREATOR = new Creator<ObjectCourse>() {
        @Override
        public ObjectCourse createFromParcel(Parcel in) {
            return new ObjectCourse(in);
        }

        @Override
        public ObjectCourse[] newArray(int size) {
            return new ObjectCourse[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(desc);
    }
}
