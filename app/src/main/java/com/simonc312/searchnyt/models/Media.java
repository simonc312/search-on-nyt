package com.simonc312.searchnyt.models;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Simon on 2/6/2016.
 */
public class Media {
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
        return getImageType("Standard Thumbnail");
    }

    public MediaMetaData getStandard() {
        return getImageType("square320");
    }

    /**
     * Returns first match of type and
     * @param type
     * @return
     */
    private MediaMetaData getImageType(String type) {
        for (int i = 0; i <metaDataList.size(); i++) {
            MediaMetaData image = metaDataList.get(i);
            Log.d("MediaMetaData format",image.format);
            if (image.format.contains(type))
                return image;
        }
        return null;
    }

    public String getAnyImageUrl() {
        String url = "";
        Media.MediaMetaData metaData = getStandard();
        if (metaData != null)
            url = metaData.url;
        else {
            metaData = getThumbnail();
            if (metaData != null)
                url = metaData.url;
        }
        return url;
    }

    public static class MediaMetaData {
        public String url;
        public String format;
        public int height;
        public int width;
        @JsonCreator
        public MediaMetaData(
                @JsonProperty(value = "url") String url,
                @JsonProperty(value = "format") String format,
                @JsonProperty(value = "height") int height,
                @JsonProperty(value = "width") int width) {
            this.url = url;
            this.format = format;
            this.height = height;
            this.width = width;
        }
    }
}
