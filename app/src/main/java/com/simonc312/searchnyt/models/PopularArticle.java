package com.simonc312.searchnyt.models;

import com.simonc312.searchnyt.helpers.DateHelper;

import java.util.List;

/**
 * Created by Simon on 1/27/2016.
 */
public class PopularArticle extends Article{

    public PopularArticle(
            String section,
            String title,
            String url,
            String byline,
            String publishedDate,
            String summary,
            List<Media> media){
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

    @Override
    public String getRelativeTimePosted() {
        return DateHelper.getInstance().getRelativeTime(this.publishedDate);
    }
}
