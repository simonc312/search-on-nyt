package com.simonc312.searchnyt.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Simon on 2/18/2016.
 */
public class Query implements Parcelable {
    private String query;

    public Query(String query){
        this.query = query;
    }

    protected Query(Parcel in) {
        query = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(query);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Query> CREATOR = new Creator<Query>() {
        @Override
        public Query createFromParcel(Parcel in) {
            return new Query(in);
        }

        @Override
        public Query[] newArray(int size) {
            return new Query[size];
        }
    };

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
