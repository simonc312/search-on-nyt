package com.simonc312.searchnyt.models;

import java.util.List;

/**
 * Created by Simon on 1/27/2016.
 */
public class SearchArticle extends Article{

    private String wordCount;

    public SearchArticle(
            String section,
            String title,
            String url,
            String byline,
            String publishedDate,
            String summary,
            List<Media> media,
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

    @Override
    public String getRelativeTimePosted() {
        return "today";
    }
}
