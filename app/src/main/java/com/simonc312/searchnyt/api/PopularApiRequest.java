package com.simonc312.searchnyt.api;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by Simon on 1/29/2016.
 */
public class PopularApiRequest extends AbstractApiRequest {
    private String API_KEY = "33b85f6537208d440f0dcbfa24c3d55d:3:74301097";
    protected String TIME_PERIOD = "1";
    protected String RESOURCE_TYPE = "mostshared";
    protected String SECTION = "all-sections";
    public PopularApiRequest(Context context,RequestListener listener){
        super(context,listener);
        addParam("api-key",API_KEY);
    }

    @Override
    String getApiKey() {
        return API_KEY;
    }

    @Override
    public String getUrl() {
        return String.format("http://api.nytimes.com/svc/mostpopular/v2/%s/%s/%s.%s",RESOURCE_TYPE,SECTION,TIME_PERIOD,RESPONSE_TYPE);
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
