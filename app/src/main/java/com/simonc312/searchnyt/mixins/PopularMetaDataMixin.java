package com.simonc312.searchnyt.mixins;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Simon on 2/17/2016.
 */
public abstract class PopularMetaDataMixin {

    PopularMetaDataMixin(@JsonProperty(value = "url") String url,
                         @JsonProperty(value = "format") String format,
                         @JsonProperty(value = "height") int height,
                         @JsonProperty(value = "width") int width) { }

}
