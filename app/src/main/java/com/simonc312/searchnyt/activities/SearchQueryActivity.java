package com.simonc312.searchnyt.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.fragments.FilterFragment;
import com.simonc312.searchnyt.helpers.DateHelper;
import com.simonc312.searchnyt.models.SearchQuery;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SearchQueryActivity extends AppCompatActivity
        implements FilterFragment.InteractionListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private SearchQuery searchQuery;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupSupportActionBar();
        setupSearchQuery();
        swapFragment(FilterFragment.newInstance());
    }

    private void setupSearchQuery() {
        String defaultBeginDate = getString(R.string.earliestSearchBeginDate);
        String defaultEndDate = DateHelper.getInstance().getFilterFormatDate(new Date());
        String defaultSections = "\"Article\"";
        searchQuery = new SearchQuery("",defaultBeginDate,defaultEndDate,defaultSections);
    }

    private void setupSupportActionBar(){
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setShowHideAnimationEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        // set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        menuItem.expandActionView();

        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;  // Return true to expand action view
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearchRequest(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void startSearchRequest(String query) {
        if(query.length() > 1){
            searchQuery.setQuery(query);
            onApplyFilter(searchQuery);
        }
    }

    protected void swapFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void onApplyFilter(SearchQuery searchQuery) {
        if(searchView != null){
            Bundle bundle = new Bundle();
            bundle.putString("title", searchQuery.getQuery());
            bundle.putParcelable("query", searchQuery);
            triggerSearch(searchQuery.getQuery(), bundle);
        }
    }

    @Override
    public void onApplyFilter(String beginDate, String endDate, String sections, boolean startSearch){
        if(beginDate != null)
            searchQuery.setBeginDate(beginDate);
        if(endDate != null)
            searchQuery.setEndDate(endDate);
        if(sections != null)
            searchQuery.setSections(sections);
        else if(beginDate == null && endDate == null)
            setupSearchQuery();
        if(startSearch){
            String query = searchView.getQuery().toString();
            startSearchRequest(query);
        }
    }
}
