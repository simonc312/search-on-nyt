package com.simonc312.searchnyt.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Created by Simon on 1/27/2016.
 */
public class PopularArticle extends Article{

    @JsonCreator
    public PopularArticle(
            @JsonProperty(value = "section") String section,
            @JsonProperty(value = "title") String title,
            @JsonProperty(value = "url") String url,
            @JsonProperty(value = "byline") String byline,
            @JsonProperty(value = "published_date") String publishedDate,
            @JsonProperty(value = "abstract") String summary,
            @JsonProperty(value = "media") List<Media> media){
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
}
