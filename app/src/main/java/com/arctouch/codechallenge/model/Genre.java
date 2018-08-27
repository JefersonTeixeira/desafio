package com.arctouch.codechallenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Genre implements Parcelable{

    public int id;
    public String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;

        Genre genre = (Genre) o;

        if (id != genre.id) return false;
        return name != null ? name.equals(genre.name) : genre.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);

    }


    public static final Parcelable.Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel parcel) {
            Genre genre = new Genre();
            genre.readFromParcel(parcel);
            return genre;
        }

        @Override
        public Genre[] newArray(int i) {
            return new Genre[0];
        }
    };

    public  void readFromParcel(Parcel parcel){
        this.id = parcel.readInt();
        this.name = parcel.readString();
    }
}
