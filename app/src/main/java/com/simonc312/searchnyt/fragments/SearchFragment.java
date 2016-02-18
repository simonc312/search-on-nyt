package com.simonc312.searchnyt.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.adapters.SearchAdapter;
import com.simonc312.searchnyt.helpers.HorizontalDividerItemDecoration;
import com.simonc312.searchnyt.models.SearchQuery;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link InteractionListener}
 * interface.
 */
public class SearchFragment extends Fragment {

    public final static int TAG_TYPE = 0;
    public final static int SEARCH_TYPE = 1;
    @BindString(R.string.action_query_changed) String ACTION_QUERY_CHANGED;
    private InteractionListener mListener;
    private SearchAdapter adapter;
    private int searchType;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFragment() {
    }

    public static SearchFragment newInstance(int searchType) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putInt("searchType",searchType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleArguments(getArguments());
    }

    private void handleArguments(Bundle arguments) {
        if(arguments != null){
            searchType = arguments.getInt("searchType");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_item_list, container, false);
        ButterKnife.bind(this, view);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            adapter = new SearchAdapter( new ArrayList<SearchQuery>(), mListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            Drawable drawable = getActivity().getResources().getDrawable(android.R.drawable.divider_horizontal_bright);
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration(drawable));
            recyclerView.setAdapter(adapter);
        }
        fetchAsyncDefault();

        return view;
    }

    private void fetchAsyncDefault() {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface InteractionListener {
        void onListFragmentInteraction(SearchQuery selectedTag);
    }
}
