package com.simonc312.searchnyt.models;

import android.os.Parcel;

import com.simonc312.searchnyt.helpers.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 1/27/2016.
 */
public class PopularArticle extends Article<PopularMedia>{

    public PopularArticle(
            String section,
            String title,
            String url,
            String byline,
            String publishedDate,
            String summary,
            List<PopularMedia> media){
        super(
                section,
                title,
                url,
                byline,
                publishedDate,
                summary,
                media
        );
    }

    public PopularArticle(Parcel in) {
        super(in);
    }

    @Override
    public String getRelativeTimePosted() {
        return DateHelper.getInstance().getRelativeTime(this.publishedDate);
    }

    @Override
    public String getCaption() {
        if(media != null && !media.isEmpty()){
            PopularMedia m = media.get(0);
            return m.caption;
        } else
            return "no caption";
    }

    @Override
    public MediaMetaData getMetaData(String format){
        List<String> formats = new ArrayList<>(4);
        formats.add(format);
        formats.add(PopularMedia.SQUARE_FORMAT);
        formats.add(PopularMedia.THUMBNAIL_FORMAT);
        formats.add(PopularMedia.NORMAL_FORMAT);
        return getAnyImageType(formats);
    }

    @Override
    public MediaMetaData getMetaData() {
        List<String> formats = new ArrayList<>(3);
        formats.add(PopularMedia.SQUARE_FORMAT);
        formats.add(PopularMedia.THUMBNAIL_FORMAT);
        formats.add(PopularMedia.NORMAL_FORMAT);
        return getAnyImageType(formats);
    }

    @Override
    public MediaMetaData getJumboMetaData() {
        List<String> formats = new ArrayList<>(4);
        formats.add(PopularMedia.JUMBO_FORMAT);
        formats.add(PopularMedia.SQUARE_FORMAT);
        formats.add(PopularMedia.THUMBNAIL_FORMAT);
        formats.add(PopularMedia.NORMAL_FORMAT);
        return getAnyImageType(formats);
    }

    private MediaMetaData getAnyImageType(List<String> formatOrderList){
        MediaMetaData source = null;
        if(media != null){
            for(int i=0;i<media.size();i++){
                PopularMedia m = media.get(i);
                source = m.getAnyImage(formatOrderList);
            }
        }
        return source;
    }

    public static final Creator<PopularArticle> CREATOR = new Creator<PopularArticle>() {
        @Override
        public PopularArticle createFromParcel(Parcel in) {
            return new PopularArticle(in);
        }

        @Override
        public PopularArticle[] newArray(int size) {
            return new PopularArticle[size];
        }
    };
}
