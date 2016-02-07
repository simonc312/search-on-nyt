package com.simonc312.searchnyt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simonc312.searchnyt.models.Article;
import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.viewHolders.GridViewHolder;
import com.simonc312.searchnyt.viewHolders.TrendingPostViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Simon on 1/26/2016.
 */
public class TrendingAdapter extends RecyclerView.Adapter<GridViewHolder>{
    private final PostItemListener mListener;
    private Context mContext;
    private boolean isGridLayout;
    private List<Article> articleList;


    public TrendingAdapter(Context context, boolean isGridLayout,PostItemListener mListener){
        articleList = new ArrayList<>();
        this.mContext = context;
        this.isGridLayout = isGridLayout;
        this.mListener = mListener;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutID = isGridLayout ? R.layout.rv_grid_item : R.layout.rv_item;
        View view = LayoutInflater.from(mContext).inflate(layoutID, parent, false);
        return isGridLayout ? new GridViewHolder(view,mListener) : new TrendingPostViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        Article data = articleList.get(position);
        holder.setPostImage(data.getImageSource());

        if(!isGridLayout){
            TrendingPostViewHolder trendingPostViewHolder = (TrendingPostViewHolder) holder;
            trendingPostViewHolder.setProfileImage(data.getWebUrl());
            trendingPostViewHolder.setUsername(data.getTitle());
            trendingPostViewHolder.setLikes(data.getDisplayByline());
            trendingPostViewHolder.setTimePosted(data.getRelativeTimePosted());
            trendingPostViewHolder.setCaption(data.getSummary());
        }
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void addPost(Article post, boolean addToEnd){
        if(addToEnd){
            addPost(post, articleList.size());
        } else{
            addPost(post,0);
        }
    }

    private void addPost(Article post, int position){
        articleList.add(position,post);
        notifyItemInserted(position);
    }

    public void addPosts(List<Article> posts){
        articleList.addAll(0, posts);
        notifyDataSetChanged();
    }

    public boolean isGridLayout(){
        return isGridLayout;
    }

    public void setIsGridLayout(boolean isGridLayout){
        this.isGridLayout = isGridLayout;
    }

    public interface PostItemListener {
        void onPostClick(int position);
    }
}
