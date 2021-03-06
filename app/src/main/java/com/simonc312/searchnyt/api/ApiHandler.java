package com.simonc312.searchnyt.api;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Simon on 1/29/2016.
 */
public class ApiHandler {
    private AsyncHttpClient client;
    private static ApiHandler singleton;

    private ApiHandler(){
        client = new AsyncHttpClient();
    }

    public static ApiHandler getInstance(){
        if(singleton == null)
            singleton = new ApiHandler();
        return singleton;
    }

    public void sendRequest(final ApiRequestInterface request){
        Log.d("request info",request.toString());
        client.get(
                request.getContext(),
                request.getUrl(),
                request.getParams(),
                new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                request.processOnSuccess(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                request.processOnFailure(res);
            }
        });
    }
}
