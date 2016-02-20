package com.simonc312.searchnyt.models;

import android.util.Log;

import com.simonc312.searchnyt.helpers.DateHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 1/27/2016.
 */
public class SearchArticle extends Article<MediaMetaData>{

    private String wordCount;

    public SearchArticle(
            String section,
            String title,
            String url,
            String byline,
            String publishedDate,
            String summary,
            List<MediaMetaData> media,
            String wordCount){
        super(
                section,
                title,
                url,
                byline,
                publishedDate,
                summary,
                media
        );

        this.wordCount = wordCount;
    }

    public String getWordCount(){
        return wordCount;
    }

    public MediaMetaData getAnyImage(List<String> formats) {
        MediaMetaData metaData;
        for(String format : formats){
            metaData = getImageFormat(format);
            if (metaData != null)
                return metaData;
        }
        Log.d("no metadata matched", media.toString());
        return null;
    }

    public MediaMetaData getImageFormat(String type) {
        for (MediaMetaData image : media){
            if (image.format != null && image.format.contains(type))
                return image;
        }
        return null;
    }

    @Override
    public String getRelativeTimePosted() {
        return DateHelper.getInstance().getSearchRelativeTime(publishedDate);
    }

    @Override
    public MediaMetaData getMetaData(String format) {
        List<String> formatList = new ArrayList<>(3);
        formatList.add(format);
        formatList.add(MediaMetaData.NORMAL_FORMAT);
        formatList.add(MediaMetaData.THUMBNAIL_FORMAT);
        return getAnyImage(formatList);
    }

    @Override
    public MediaMetaData getMetaData() {
        List<String> formatList = new ArrayList<>(2);
        formatList.add(MediaMetaData.NORMAL_FORMAT);
        formatList.add(MediaMetaData.THUMBNAIL_FORMAT);
        return getAnyImage(formatList);
    }

    @Override
    public MediaMetaData getJumboMetaData() {
        List<String> formatList = new ArrayList<>(3);
        formatList.add(MediaMetaData.JUMBO_FORMAT);
        formatList.add(MediaMetaData.NORMAL_FORMAT);
        formatList.add(MediaMetaData.THUMBNAIL_FORMAT);
        return getAnyImage(formatList);
    }
}
