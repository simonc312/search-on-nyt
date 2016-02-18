package com.simonc312.searchnyt.mixins;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simonc312.searchnyt.models.MediaMetaData;

import java.util.List;

/**
 * Created by Simon on 2/17/2016.
 */
public abstract class PopularMediaMixin {

    PopularMediaMixin(@JsonProperty(value = "caption") String caption,
                      @JsonProperty(value = "type") String type,
                      @JsonProperty(value = "copyright") String copyright,
                      @JsonProperty(value = "media-metadata") List<MediaMetaData> metaDataList) { }

}
