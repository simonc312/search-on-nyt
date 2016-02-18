package com.simonc312.searchnyt.models;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

/**
 * Created by Simon on 2/6/2016.
 */
public class Media {
    public static final String JUMBO_FORMAT = "Jumbo";
    public static final String SQUARE_FORMAT = "square320";
    public static final String SQUARE_LARGE_FORMAT = "square640";
    public static final String THUMBNAIL_FORMAT = "Large Thumbnail";
    public static final String NORMAL_FORMAT = "Normal";
    public static final String MEDIUM_FORMAT = "mediumThreeByTwo440";
    public static final String MEDIUM_SMALL_FORMAT = "mediumThreeByTwo210";
    public static final String[] THREE_SQUARES = new String[]{SQUARE_FORMAT,SQUARE_FORMAT,SQUARE_FORMAT};
    public static final String[] MEDIUM_THEN_SQUARE = new String[]{MEDIUM_FORMAT,SQUARE_FORMAT};
    public static final String[] SQUARE_THEN_MEDIUM = new String[]{SQUARE_FORMAT,MEDIUM_FORMAT};
    public static final String[] LARGE_SQUARE_THEN_TWO_SQUARES = new String[]{SQUARE_LARGE_FORMAT,SQUARE_FORMAT,SQUARE_FORMAT};
    public String caption;
    public String type;
    public String copyright;
    public List<MediaMetaData> metaDataList;

    @JsonCreator
    public Media(
            @JsonProperty(value = "caption")String caption,
            @JsonProperty(value = "type") String type,
            @JsonProperty(value = "copyright") String copyright,
            @JsonProperty(value = "media-metadata") List<MediaMetaData> metaDataList) {
        this.caption = caption;
        this.type = type;
        this.copyright = copyright;
        this.metaDataList = metaDataList;
    }

    public MediaMetaData getThumbnail() {
        return getImageFormat(THUMBNAIL_FORMAT);
    }

    public MediaMetaData getSquare() {
        return getImageFormat(SQUARE_FORMAT);
    }

    public MediaMetaData getJumbo(){ return getImageFormat(JUMBO_FORMAT);}

    public MediaMetaData getAnyImage(List<String> formats) {
        Media.MediaMetaData metaData;
        for(String format : formats){
            metaData = getImageFormat(format);
            if (metaData != null)
                return metaData;
        }
        Log.d("no metadata matched", metaDataList.toString());
        return null;
    }

    /**
     * Returns first match of type and
     * @param type
     * @return
     */
    public MediaMetaData getImageFormat(String type) {
        for (int i = 0; i <metaDataList.size(); i++) {
            MediaMetaData image = metaDataList.get(i);
            if (image.format.contains(type))
                return image;
        }
        return null;
    }

    public static class MediaMetaData {
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

        @Override
        public String toString(){
            return String.format("url : %s, format : %s",url,format);
        }
    }
}
