package com.simonc312.searchnyt.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Simon on 1/31/2016.
 */
public class SearchQuery extends Query implements Parcelable {
    private String beginDate;
    private String endDate;
    private String sections;

    public SearchQuery(String query, String beginDate, String endDate, String sections){
        super(query);
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.sections = sections;
    }

    protected SearchQuery(Parcel in) {
        super(in);
        beginDate = in.readString();
        endDate = in.readString();
        sections = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(beginDate);
        dest.writeString(endDate);
        dest.writeString(sections);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchQuery> CREATOR = new Creator<SearchQuery>() {
        @Override
        public SearchQuery createFromParcel(Parcel in) {
            return new SearchQuery(in);
        }

        @Override
        public SearchQuery[] newArray(int size) {
            return new SearchQuery[size];
        }
    };

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getSections(){return sections;}

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setSections(String sections){this.sections = sections;}
}
