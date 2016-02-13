package com.simonc312.searchnyt.api;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by Simon on 1/29/2016.
 */
public class TopStoriesSearchApiRequest extends AbstractApiRequest {
    private static String API_KEY = "3870faed54170b7df07426f339eb9f74:12:74301097";
    private String section;
    public TopStoriesSearchApiRequest(Context context, RequestListener listener){
        super(context, listener);
    }

    @Override
    String getApiKey() {
        return API_KEY;
    }

    public void setQuery(String query){
        putParam("q", query);
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String getUrl() {
        return String.format("http://api.nytimes.com/svc/topstories/v1/%s.%s",section, RESPONSE_TYPE);
    }

    @Override
    public void processOnSuccess(JSONObject jsonResponse) {
        super.processOnSuccess(jsonResponse);
    }

    @Override
    public void processOnFailure(String response) {
        super.processOnFailure(response);
    }


}

