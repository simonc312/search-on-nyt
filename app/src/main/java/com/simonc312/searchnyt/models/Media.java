package com.simonc312.searchnyt.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Simon on 2/6/2016.
 */
public class Media {
    public static final String JUMBO_FORMAT = "Jumbo";
    public static final String SQUARE_FORMAT = "square320";
    public static final String THUMBNAIL_FORMAT = "Large Thumbnail";
    public static final String MEDIUM_FORMAT = "mediumThreeByTwo440";
    public static final String MEDIUM_SMALL_FORMAT = "mediumThreeByTwo210";
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

    public String getAnyImageUrl(List<String> formats) {
        Media.MediaMetaData metaData;
        for(String format : formats){
            metaData = getImageFormat(format);
            if (metaData != null)
                return metaData.url;
        }
        return "";
    }

    /**
     * Returns first match of type and
     * @param type
     * @return
     */
    private MediaMetaData getImageFormat(String type) {
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
