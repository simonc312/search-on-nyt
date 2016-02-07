package com.simonc312.searchnyt.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Simon on 1/27/2016.
 */
public class Article {

    private final String title;
    private final String byline;
    private final String publishedDate;
    private final String summary;
    private final String url;
    public List<Media> media;

    @JsonCreator
    public Article(
            @JsonProperty(value = "title") String title,
            @JsonProperty(value = "url") String url,
            @JsonProperty(value = "byline") String byline,
            @JsonProperty(value = "published_date") String publishedDate,
            @JsonProperty(value = "abstract") String summary,
            @JsonProperty(value = "media") List<Media> media){
        this.title = title;
        this.url = url;
        this.byline = byline;
        this.publishedDate = publishedDate;
        this.summary = summary;
        this.media = media;
    }

    public String getTitle() {
        return title;
    }

    public String getByline() {
        return byline;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getImageSource() {
        String source = "empty";
        if(media != null){
            for(int i=0;i<media.size();i++){
                Media m = media.get(i);
                source = m.getAnyImageUrl();
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
        //in seconds
        long timeDifference = (System.currentTimeMillis()/1000 - Long.valueOf(this.publishedDate));
        String[] timeUnits = new String[]{"Just now","min","h","d","w","m","y"};
        int[] incrementFactors = new int[]{60,60,24,7,4,12};
        int index = 0;
        while(index < incrementFactors.length && timeDifference > incrementFactors[index]){
            timeDifference /= incrementFactors[index];
            index++;
        }
        int formattedTimeDifference = Math.round(timeDifference);

        return (index == 0) ? timeUnits[index] : String.format("%d%s",formattedTimeDifference,timeUnits[index]);
    }

    public String getDisplayByline(){
        return String.format("%s",this.byline);
    }
}
