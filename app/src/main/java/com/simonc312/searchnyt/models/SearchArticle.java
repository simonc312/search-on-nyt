package com.simonc312.searchnyt.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Simon on 1/27/2016.
 */
public class SearchArticle extends Article{

    private String wordCount;

    @JsonCreator
    public SearchArticle(
            @JsonProperty(value = "section") String section,
            @JsonProperty(value = "title") String title,
            @JsonProperty(value = "web_url") String url,
            @JsonProperty(value = "byline") String byline,
            @JsonProperty(value = "pub_date") String publishedDate,
            @JsonProperty(value = "abstract") String summary,
            @JsonProperty(value = "multimedia") List<Media> media, //TODO change Media to SearchMedia to map format to subtype
            @JsonProperty(value = "word_count") String wordCount){
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
}
