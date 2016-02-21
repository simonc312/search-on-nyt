package com.simonc312.searchnyt.api;

import android.content.Context;

/**
 * Created by Simon on 2/13/2016.
 */
public class SearchApiRequest extends AbstractApiRequest {

    private static String API_KEY = "cea9a22362595278fb0cb720a607197a:4:74301097";

    public SearchApiRequest(Context context, RequestListener listener) {
        super(context, listener);
        putParam("api-key",API_KEY);
    }

    public void setQuery(String query){
        putParam("q",query);
    }

    public void setBeginDate(String date){
        putParam("begin_date",date);
    }


    public void setEndDate(String endDate) {
        putParam("end_date", endDate);
    }

    public void setPage(int page){
        putParam("page",page);
    }

    public void setSections(String sections){
        if(sections != null && !sections.isEmpty())
            putParam("fq",String.format("section_name:(%s)",sections));
    }

    @Override
    String getApiKey() {
        return API_KEY;
    }

    @Override
    public String getUrl() {
        return String.format("http://api.nytimes.com/svc/search/v2/articlesearch.%s",RESPONSE_TYPE);
    }

}
