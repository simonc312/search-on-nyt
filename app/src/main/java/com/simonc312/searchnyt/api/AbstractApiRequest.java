package com.simonc312.searchnyt.api;

import android.content.Context;

import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by Simon on 1/29/2016.
 */
public abstract class AbstractApiRequest implements ApiRequestInterface {
    private RequestParams requestParams;
    protected String RESPONSE_TYPE = "json";
    protected Context context;
    protected RequestListener listener;

    public AbstractApiRequest(Context context, RequestListener listener){
        this.context = context;
        this.listener = listener;
        requestParams = new RequestParams();
    }

    protected void putParam(String key, String value){
        requestParams.add(key,value);
    }

    protected void putParam(String key, int value){ requestParams.put(key,value);}

    abstract String getApiKey();

    @Override
    public String getUrl() {return "OVERRIDE URL";}

    @Override
    public RequestParams getParams() {
        return requestParams;
    }

    @Override
    public Context getContext(){
        return context;
    }

    @Override
    public void processOnSuccess(JSONObject jsonResponse) {
        listener.onSuccess(jsonResponse);
    }

    @Override
    public void processOnFailure(String response) {
        listener.onFailure(response);
    }

    public interface RequestListener{
        void onSuccess(JSONObject response);
        void onFailure(String response);
    }

    @Override
    public String toString(){
        return String.format("url: %s \nkey: %s",getUrl(),getApiKey());
    }
}
