package com.simonc312.searchnyt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.fragments.TrendingFragment;

import butterknife.BindString;


public class MainActivity extends BaseActivity {
    @BindString(R.string.app_name_title) String APP_NAME_TITLE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(APP_NAME_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        swapFragment(TrendingFragment.newInstance(TrendingFragment.STAGGERED_LAYOUT, null, TrendingFragment.TRENDING_TYPE));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(this, SearchQueryActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onLayoutChange(boolean show) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }
}
