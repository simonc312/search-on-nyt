package com.simonc312.searchnyt.mixins;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simonc312.searchnyt.models.Media;

import java.util.List;

/**
 * Created by Simon on 2/17/2016.
 */
public abstract class SearchArticleMixin {

    SearchArticleMixin(@JsonProperty(value = "section") String section,
                       @JsonProperty(value = "title") String title,
                       @JsonProperty(value = "web_url") String url,
                       @JsonProperty(value = "byline") String byline,
                       @JsonProperty(value = "pub_date") String publishedDate,
                       @JsonProperty(value = "abstract") String summary,
                       @JsonProperty(value = "multimedia") List<Media> media,
                       @JsonProperty(value = "word_count") String wordCount) { }

}
