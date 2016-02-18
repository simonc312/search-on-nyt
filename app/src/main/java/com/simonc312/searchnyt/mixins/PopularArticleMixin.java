package com.simonc312.searchnyt.mixins;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simonc312.searchnyt.models.PopularMedia;

import java.util.List;

/**
 * Created by Simon on 2/17/2016.
 */
public abstract class PopularArticleMixin {

    PopularArticleMixin(@JsonProperty(value = "section") String section,
                        @JsonProperty(value = "title") String title,
                        @JsonProperty(value = "url") String url,
                        @JsonProperty(value = "byline") String byline,
                        @JsonProperty(value = "published_date") String publishedDate,
                        @JsonProperty(value = "abstract") String summary,
                        @JsonProperty(value = "media") List<PopularMedia> media) { }

}
