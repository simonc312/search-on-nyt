package com.simonc312.searchnyt.activities;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.simonc312.searchnyt.fragments.SearchFragment;

import static com.simonc312.searchnyt.fragments.SearchFragment.SEARCH_TYPE;

import com.simonc312.searchnyt.helpers.DateHelper;
import com.simonc312.searchnyt.models.SearchQuery;
import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.viewPagers.ViewPagerAdapter;

import java.util.Date;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;


public class SearchQueryActivity extends AppCompatActivity
        implements SearchFragment.InteractionListener {
    @BindDrawable(R.drawable.ic_favorite_red_500_18dp)
    Drawable FILTER_DRAWABLE;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    private SearchQuery searchQuery;
    private SearchView searchView;
    private ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_query);
        ButterKnife.bind(this);
        setupSupportActionBar();
        setupViewPager();
        setupSearchQuery();
    }

    private void setupSearchQuery() {
        String defaultBeginDate = getString(R.string.earliestSearchBeginDate);
        String defaultEndDate = DateHelper.getInstance().getFilterFormatDate(new Date());
        searchQuery = new SearchQuery("",defaultBeginDate,defaultEndDate);
    }

    private void setupViewPager() {
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(SearchFragment.newInstance(SEARCH_TYPE), "Filters", FILTER_DRAWABLE);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupSupportActionBar(){
        setSupportActionBar(toolbar);
        //getSupportActionBar().setHideOnContentScrollEnabled(true);
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
            case R.id.action_search:
                break;
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
                // Do something when collapsed
                finish();
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length() > 1){
                    searchQuery.setQuery(query);
                    onListFragmentInteraction(searchQuery);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onListFragmentInteraction(SearchQuery searchQuery) {
        if(searchView != null){
            int queryType = SearchFragment.SEARCH_TYPE;
            Bundle bundle = new Bundle();
            bundle.putString("title", searchQuery.getQuery());
            bundle.putInt("queryType", queryType);
            bundle.putParcelable("query",searchQuery);
            triggerSearch(searchQuery.getQuery(), bundle);
        }
    }
}
