package com.simonc312.searchnyt.models;

/**
 * Created by Simon on 1/27/2016.
 */
public class Article {

    private String title;
    private String byline;
    private String publishedDate;
    private String summary;
    private String imageSource;
    private String webUrl;


    public Article(){
        this.title = "Cancer Cure Found";
        this.byline = "by Simon Chen";
        this.publishedDate = "2010-08-31";
        this.summary = "Abstract summary summary";
        this.imageSource = "iv_image source";
        this.webUrl = "web url";
    }

    public Article(String title, String webUrl, String byline, String publishedDate, String summary, String imageSource){
        this.title = title;
        this.webUrl = webUrl;
        this.byline = byline;
        this.publishedDate = publishedDate;
        this.summary = summary;
        this.imageSource = imageSource;
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
        return imageSource;
    }

    public String getSummary() {
        return summary;
    }

    public String getWebUrl() {
        return webUrl;
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
