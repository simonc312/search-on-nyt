package com.simonc312.searchnyt.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simonc312.searchnyt.fragments.SearchFragment.InteractionListener;
import com.simonc312.searchnyt.models.SearchQuery;
import com.simonc312.searchnyt.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link SearchQuery} and makes a call to the
 * specified {@link InteractionListener}.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private static final int TAG_TYPE = 0;
    private List<SearchQuery> mValues;
    private final InteractionListener mListener;

    public SearchAdapter(List<SearchQuery> items,InteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return TAG_TYPE;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutResourceId = R.layout.fragment_search_item;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutResourceId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SearchQuery tag = mValues.get(position);
        holder.mItem = tag;
        String name;
        name = tag.getQuery();
        holder.mIdView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addItem(SearchQuery tag) {
        mValues.add(0,tag);
        notifyItemInserted(0);
    }

    public void update(List<SearchQuery> newList) {
        mValues = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        @Nullable
        public final TextView mContentView;
        public final ImageView imageView;
        public SearchQuery mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            imageView = (ImageView) view.findViewById(R.id.image);
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            mListener.onListFragmentInteraction(mItem);
        }
    }
}
