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

        if(!isGridLayout){
            TrendingPostViewHolder trendingPostViewHolder = (TrendingPostViewHolder) holder;
            trendingPostViewHolder.setPostImage(data.getImageSource());
            trendingPostViewHolder.setSection(data.getSection());
            trendingPostViewHolder.setHeadline(data.getTitle());
            trendingPostViewHolder.setPublishedDate(data.getPublishedDate());
        } else {
            holder.setPostImage(data.getImageSource());
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
        articleList.add(position, post);
        notifyItemInserted(position);
    }

    public void addPosts(List<Article> posts, boolean addToEnd){
        int startIndex = addToEnd ? articleList.size() : 0;
        articleList.addAll(startIndex, posts);
        notifyItemRangeChanged(startIndex, posts.size());
    }

    public void setIsGridLayout(boolean isGridLayout){
        this.isGridLayout = isGridLayout;
    }

    public void update(List<Article> articleList, boolean addToEnd) {
        if(addToEnd){
            addPosts(articleList,addToEnd);
        } else{
            addNewPosts(articleList);
        }
    }

    private void addNewPosts(List<Article> newList) {
        List<Article> comparisonList;
        if(newList.size() > articleList.size())
            comparisonList = articleList;
        else
            comparisonList = this.articleList.subList(0, newList.size());
        for(Article article : newList){
            if(!comparisonList.contains(article))
                addPost(article,0);
        }
    }

    public interface PostItemListener {
        void onPostClick(int position);
    }
}
