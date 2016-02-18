package com.simonc312.searchnyt.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.simonc312.searchnyt.adapters.TrendingAdapter;
import com.simonc312.searchnyt.api.AbstractApiRequest;
import com.simonc312.searchnyt.api.ApiRequestInterface;
import com.simonc312.searchnyt.api.ApiHandler;
import com.simonc312.searchnyt.api.PopularApiRequest;
import com.simonc312.searchnyt.api.SearchApiRequest;
import com.simonc312.searchnyt.helpers.HorizontalDividerItemDecoration;
import com.simonc312.searchnyt.mixins.PopularArticleMixin;
import com.simonc312.searchnyt.mixins.PopularMediaMixin;
import com.simonc312.searchnyt.mixins.PopularMetaDataMixin;
import com.simonc312.searchnyt.mixins.SearchArticleDeserializer;
import com.simonc312.searchnyt.mixins.SearchMediaMixin;
import com.simonc312.searchnyt.models.Article;
import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.helpers.EndlessRVScrollListener;
import com.simonc312.searchnyt.helpers.GridItemDecoration;
import com.simonc312.searchnyt.models.MediaMetaData;
import com.simonc312.searchnyt.models.PopularArticle;
import com.simonc312.searchnyt.models.PopularMedia;
import com.simonc312.searchnyt.models.SearchArticle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InteractionListener} interface
 * to handle interaction events.
 */
public class TrendingFragment extends Fragment
        implements TrendingAdapter.PostItemListener,
        AbstractApiRequest.RequestListener{
    public static final int GRID_LAYOUT = 0;
    public static final int LINEAR_LAYOUT = 1;
    public static final int STAGGERED_LAYOUT = 2;
    public static final int TRENDING_TYPE = -123;
    @BindInt(R.integer.grid_layout_span_count)
    int GRID_LAYOUT_SPAN_COUNT;
    @BindInt(R.integer.grid_layout_item_spacing)
    int GRID_LAYOUT_ITEM_SPACING;
    @BindString(R.string.action_back_pressed)
    String ACTION_BACK_PRESSED;
    @Bind(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @Bind(R.id.rvItems)
    RecyclerView recyclerView;

    private InteractionListener mListener;
    private BackPressedBroadcastListener backPressedListener;
    private TrendingAdapter adapter;
    private RecyclerView.ItemDecoration gridItemDecoration;
    private RecyclerView.ItemDecoration horizontalItemDecoration;
    private String query;
    //determines where to add new posts
    private boolean addToEnd = false;
    private int queryType;
    //determines which page to request
    private int currentPage = 0;
    private int requestedLayout;

    public TrendingFragment() {
        // Required empty public constructor
    }

    public static TrendingFragment newInstance(int layoutRequested,String query, int queryType){
        Bundle bundle = new Bundle();
        bundle.putInt("layoutRequested",layoutRequested);
        bundle.putString("query", query);
        bundle.putInt("queryType",queryType);
        TrendingFragment fragment = new TrendingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        backPressedListener = new BackPressedBroadcastListener();
        handleArguments(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending_main,container,false);
        ButterKnife.bind(this, view);
        setupRV(recyclerView, getContext());
        setupSwipeToRefresh(swipeContainer);
        fetchAsync();
        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InteractionListener) {
            mListener = (InteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart(){
        super.onStart();
        IntentFilter filter = new IntentFilter(ACTION_BACK_PRESSED);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(backPressedListener, filter);
    }

    @Override
    public void onPause(){
        super.onPause();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(backPressedListener);
    }

    @Override
    public void onPostClick(int position){
        if(useGridLayout()) {
            setRequestedLayout(LINEAR_LAYOUT);
            mListener.onLayoutChange(true);
            adapter.setIsGridLayout(false);
            updateRV(recyclerView, getLinearLayout(), adapter);
            recyclerView.scrollToPosition(position);
        }
    }

    @Override
    public void onSuccess(JSONObject response) {
        Log.d("response",response.toString());
        if(query != null)
            handleSearchSuccessResponse(response);
        else
            handleSuccessResponse(response);
        stopRefresh();
    }

    @Override
    public void onFailure(String response) {
        handleErrorResponse(response);
        stopRefresh();
    }

    /**
     * When back button is pressed and fragment is visible, if linear layout revert to grid layout
     and scroll to current position
     */
    public void onBackPressed() {
        if(!useGridLayout()){
            setRequestedLayout(STAGGERED_LAYOUT);
            mListener.onLayoutChange(false);
            adapter.setIsGridLayout(true);
            updateRV(recyclerView,getLayout(),adapter);
            recyclerView.scrollToPosition(recyclerView.getChildAdapterPosition(recyclerView.getFocusedChild()));
        } else {
            mListener.onBackPress(true);
        }
    }

    private void handleArguments(Bundle bundle) {
        if(!bundle.isEmpty()){
            requestedLayout = bundle.getInt("layoutRequested",STAGGERED_LAYOUT);
            query = bundle.getString("query");
            queryType = bundle.getInt("queryType",TRENDING_TYPE);
        }
    }

    private void setupSwipeToRefresh(SwipeRefreshLayout swipeContainer) {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addToEnd = false;
                currentPage = 0;
                fetchAsync();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void stopRefresh(){
        if(swipeContainer != null){
            swipeContainer.setRefreshing(false);
        }
    }

    private void fetchAsync() {
        if(query == null)
            fetchPopularAsync();
        else{
            fetchSearchAsync(query);
        }
    }

    private void setupRV(RecyclerView recyclerView, Context context) {
        if(adapter == null){
            adapter = new TrendingAdapter(context,useGridLayout(),this);
        }

        if(gridItemDecoration == null){
            gridItemDecoration = new GridItemDecoration(GRID_LAYOUT_SPAN_COUNT,GRID_LAYOUT_ITEM_SPACING,false);
        }

        if(horizontalItemDecoration == null){
            Drawable drawable = getActivity().getResources().getDrawable(android.R.drawable.divider_horizontal_bright);
            horizontalItemDecoration = new HorizontalDividerItemDecoration(drawable);
        }

        recyclerView.addOnScrollListener(new EndlessRVScrollListener() {
            @Override
            public void onLoadMore(int current_page) {
                addToEnd = true;
                currentPage = current_page;
                fetchAsync();
            }
        });

        updateRV(recyclerView, getLayout(), adapter);
    }

    private RecyclerView.LayoutManager getLayout(){
        switch(requestedLayout){
            case STAGGERED_LAYOUT:
                return getStaggeredLayout();
            case LINEAR_LAYOUT:
                return getLinearLayout();
            case GRID_LAYOUT:
            default:
                return getGridLayout();
        }
    }

    private StaggeredGridLayoutManager getStaggeredLayout(){
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(GRID_LAYOUT_SPAN_COUNT,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        return manager;
    }

    private GridLayoutManager getGridLayout(){
        return new GridLayoutManager(getContext(), GRID_LAYOUT_SPAN_COUNT);
    }
    private LinearLayoutManager getLinearLayout(){
        return new LinearLayoutManager(getContext());
    }

    public void setRequestedLayout(int requestedLayout){
        this.requestedLayout = requestedLayout;
    }

    private void updateRV(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, RecyclerView.Adapter adapter){
        //need to remove it otherwise
        if(layoutManager instanceof GridLayoutManager)
            updateItemDecoration(gridItemDecoration,horizontalItemDecoration);
        else if(layoutManager instanceof LinearLayoutManager)
            updateItemDecoration(horizontalItemDecoration, gridItemDecoration);
        else if(layoutManager instanceof StaggeredGridLayoutManager)
            recyclerView.removeItemDecoration(horizontalItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void updateItemDecoration(RecyclerView.ItemDecoration newDecoration, RecyclerView.ItemDecoration oldDecoration){
        if(oldDecoration != null && newDecoration != null)
            recyclerView.removeItemDecoration(oldDecoration);
            recyclerView.addItemDecoration(newDecoration);
    }

    private boolean useGridLayout(){
        return requestedLayout == GRID_LAYOUT || requestedLayout == STAGGERED_LAYOUT;
    }

    private void fetchPopularAsync() {
        PopularApiRequest request = new PopularApiRequest(getContext(), this);
        request.setPage(currentPage);
        sendRequest(request);
    }

    private void fetchSearchAsync(String tag){
        SearchApiRequest request = new SearchApiRequest(getContext(),this);
        request.setQuery("pope");
        request.setBeginDate("20150210");
        request.setOffset(adapter.getItemCount());
        sendRequest(request);
    }

    private void sendRequest(ApiRequestInterface request){
        ApiHandler.getInstance().sendRequest(request);
    }

    private void handleSuccessResponse(JSONObject response) {
        try {
            JSONArray dataArray = response.getJSONArray("results");
            ObjectReader reader = new ObjectMapper()
                    .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
                    .addMixIn(PopularArticle.class, PopularArticleMixin.class)
                    .addMixIn(PopularMedia.class,PopularMediaMixin.class)
                    .addMixIn(MediaMetaData.class, PopularMetaDataMixin.class)
                    .reader()
                    .with(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                    .forType(new TypeReference<List<PopularArticle>>() {});
            List<Article> articleList = reader.readValue(dataArray.toString());
            adapter.update(articleList, addToEnd);


        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSearchSuccessResponse(JSONObject response) {
        try {
            JSONObject responseObject = response.getJSONObject("response");
            JSONArray dataArray = responseObject.getJSONArray("docs");
            SimpleModule module = new SimpleModule();
            module.addDeserializer(SearchArticle.class, new SearchArticleDeserializer());
            ObjectReader reader = new ObjectMapper()
                    .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .registerModule(module)
                    .reader()
                    .with(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                    .forType(new TypeReference<List<SearchArticle>>() {});
            List<Article> articleList = reader.readValue(dataArray.toString());
            adapter.update(articleList, addToEnd);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleErrorResponse(String response) {
        Log.e("Error", response);
    }

    /**
     * Probably needs to be moved outside since other recyclerviews will use it
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface InteractionListener {

        void onLayoutChange(boolean show);

        void onBackPress(boolean pop);
    }

    private class BackPressedBroadcastListener extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            onBackPressed();
        }
    }
}
