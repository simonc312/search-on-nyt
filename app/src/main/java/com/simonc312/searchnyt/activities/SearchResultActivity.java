package com.simonc312.searchnyt.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

import com.simonc312.searchnyt.fragments.TrendingFragment;
import com.simonc312.searchnyt.models.Query;


public class SearchResultActivity extends BaseActivity {

    private String TITLE;
    private Query QUERY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleSearchIntent(getIntent());
        setTitle(TITLE);
        swapFragment(TrendingFragment.newInstance(TrendingFragment.STAGGERED_LAYOUT, QUERY));
    }

    @Override
    public void onNewIntent(Intent intent){
        setIntent(intent);
        handleSearchIntent(intent);
    }

    @Override
    public void onLayoutChange(boolean show) {
        //do nothing.
    }

    private void handleSearchIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            Bundle bundle = intent.getBundleExtra(SearchManager.APP_DATA);
            QUERY = bundle.getParcelable("query");
            TITLE = bundle.getString("title");
        }
    }

}
