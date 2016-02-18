package com.simonc312.searchnyt.models;

import android.util.Patterns;

/**
 * Created by Simon on 2/17/2016.
 */
public class MediaMetaData {
    public static String JUMBO_FORMAT = "xlarge";
    public static String THUMBNAIL_FORMAT = "thumbnail";
    public static String NORMAL_FORMAT = "wide";
    public static String baseUrl = "http://graphics8.nytimes.com/";
    public String url;
    public String format;
    public int height;
    public int width;
    public MediaMetaData(
            String url,
            String format,
            int height,
            int width) {
        this.url = url;
        this.format = format;
        this.height = height;
        this.width = width;
    }

    /**
     * Issue with Most Popular API metadata url is that it contains valid url link
     * while Search API media url does not.
     * @return
     */
    public String getUrl(){
        if(Patterns.WEB_URL.matcher(url).matches())
            return url;
        else
            return String.format("%s%s",baseUrl,url);
    }

    @Override
    public String toString(){
        return String.format("url : %s, format : %s",url,format);
    }
}
