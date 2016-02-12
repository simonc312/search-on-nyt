package com.simonc312.searchnyt.models;

import android.content.Context;
import android.text.format.DateUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.simonc312.searchnyt.helpers.DateHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Simon on 1/27/2016.
 */
public class Article {

    private final String section;
    private final String title;
    private final String byline;
    private final String publishedDate;
    private final String summary;
    private final String url;

    public List<Media> media;


    @JsonCreator
    public Article(
            @JsonProperty(value = "section") String section,
            @JsonProperty(value = "title") String title,
            @JsonProperty(value = "url") String url,
            @JsonProperty(value = "byline") String byline,
            @JsonProperty(value = "published_date") String publishedDate,
            @JsonProperty(value = "abstract") String summary,
            @JsonProperty(value = "media") List<Media> media){
        this.section = section;
        this.title = title;
        this.url = url;
        this.byline = byline;
        this.publishedDate = publishedDate;
        this.summary = summary;
        this.media = media;
    }

    public String getSection(){return section;};

    public String getTitle() {
        return title;
    }

    public String getByline() {
        return byline;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getCaption() {
        if(media != null && !media.isEmpty()){
            Media m = media.get(0);
            return m.caption;
        } else
            return "no caption";
    }

    public String getImageSource() {
        String source = "empty";
        if(media != null){
            List<String> formats = new ArrayList<>(2);
            formats.add(Media.SQUARE_FORMAT);
            formats.add(Media.THUMBNAIL_FORMAT);
            for(int i=0;i<media.size();i++){
                Media m = media.get(i);
                source = m.getAnyImageUrl(formats);
            }
        }
            return source;
    }

    public String getJumboImageSource() {
        String source = "empty";
        if(media != null){
            List<String> formats = new ArrayList<>(3);
            formats.add(Media.JUMBO_FORMAT);
            formats.add(Media.SQUARE_FORMAT);
            formats.add(Media.THUMBNAIL_FORMAT);

            for(int i=0;i<media.size();i++){
                Media m = media.get(i);
                source = m.getAnyImageUrl(formats);
            }
        }
        return source;
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
    }

    public String getRelativeTimePosted(){
        return DateHelper.getInstance().getRelativeTime(this.publishedDate);
    }

    public String getDisplayByline(){
        return String.format("%s",this.byline);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;

        Article article = (Article) o;

        if (title != null ? !title.equals(article.title) : article.title != null) return false;
        if (byline != null ? !byline.equals(article.byline) : article.byline != null) return false;
        if (publishedDate != null ? !publishedDate.equals(article.publishedDate) : article.publishedDate != null)
            return false;
        if (summary != null ? !summary.equals(article.summary) : article.summary != null)
            return false;
        return !(url != null ? !url.equals(article.url) : article.url != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (byline != null ? byline.hashCode() : 0);
        result = 31 * result + (publishedDate != null ? publishedDate.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

}
