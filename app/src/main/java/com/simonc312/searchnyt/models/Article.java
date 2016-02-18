package com.simonc312.searchnyt.models;

import android.text.Html;
import android.text.Spanned;

import java.util.List;

/**
 * Created by Simon on 1/27/2016.
 */
public abstract class Article<MediaType> {

    private final String section;
    private final String title;
    private final String byline;
    protected final String publishedDate;
    private final String summary;
    private final String url;

    public List<MediaType> media;

    public Article(
            String section,
            String title,
            String url,
            String byline,
            String publishedDate,
            String summary,
            List<MediaType> media){
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
        return "caption";
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
    }

    public String getDisplayByline(){
        return String.format("%s", this.byline);
    }

    public Spanned getDisplayTitle(){ return Html.fromHtml(title);}

    public abstract String getRelativeTimePosted();

    public abstract MediaMetaData getMetaData(String format);

    public abstract MediaMetaData getMetaData();

    public abstract MediaMetaData getJumboMetaData();

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
