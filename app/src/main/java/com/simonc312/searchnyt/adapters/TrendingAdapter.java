package com.simonc312.searchnyt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simonc312.searchnyt.models.Article;
import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.models.Media;
import com.simonc312.searchnyt.viewHolders.FirstTrendingPostViewHolder;
import com.simonc312.searchnyt.viewHolders.GridViewHolder;
import com.simonc312.searchnyt.viewHolders.TrendingPostViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Simon on 1/26/2016.
 */
public class TrendingAdapter extends RecyclerView.Adapter<GridViewHolder>{
    private static final int FIRST_ITEM_VIEW_TYPE = 1;
    private static final int NORMAL_ITEM_VIEW_TYPE = 0;
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
        final int layoutID;
        View view;
        if(viewType == FIRST_ITEM_VIEW_TYPE && !isGridLayout){
            layoutID = R.layout.rv_first_item;
            view = LayoutInflater.from(mContext).inflate(layoutID, parent, false);
            return new FirstTrendingPostViewHolder(view,mListener);
        }
        else{
            layoutID = isGridLayout ? R.layout.rv_grid_item : R.layout.rv_item;
            view = LayoutInflater.from(mContext).inflate(layoutID, parent, false);
            return isGridLayout ? new GridViewHolder(view, mListener) : new TrendingPostViewHolder(view, mListener);
        }
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        Article data = articleList.get(position);
        Media.MediaMetaData metaData;
        if(!isGridLayout){
            TrendingPostViewHolder trendingPostViewHolder = (TrendingPostViewHolder) holder;
            trendingPostViewHolder.setSection(data.getSection());
            trendingPostViewHolder.setHeadline(data.getDisplayTitle());
            trendingPostViewHolder.setPublishedDate(
                    data.getRelativeTimePosted()
            );
            if(holder.getItemViewType() == FIRST_ITEM_VIEW_TYPE) {
                FirstTrendingPostViewHolder firstTrendingPostViewHolder = (FirstTrendingPostViewHolder) holder;
                firstTrendingPostViewHolder.setCaption(data.getCaption());
                metaData = data.getJumboMetaData();
                if(metaData != null)
                    firstTrendingPostViewHolder.setPostImage(metaData.url);
            } else{
                metaData = data.getMetaData();
                if(metaData != null)
                    trendingPostViewHolder.setPostImage(metaData.url);
            }
        } else {
            String format = getNextFormat(position);
            metaData = data.getMetaData(format);
            if(metaData != null)
                holder.setPostImage(metaData.url,metaData.width,metaData.height);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? FIRST_ITEM_VIEW_TYPE : NORMAL_ITEM_VIEW_TYPE;
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
        for(int i=newList.size()-1;i>=0;i--){
            Article article = newList.get(i);
            if(comparisonList.contains(article))
                newList.remove(i);
        }
        addPosts(newList,false);
    }

    private String getNextFormat(int position) {
        String format = Media.SQUARE_FORMAT;
        if(position % 3 == 0)
            format = Media.MEDIUM_FORMAT;
        else if(position % 7 == 0)
            format = Media.NORMAL_FORMAT;
        return format;
    }

    public interface PostItemListener {
        void onPostClick(int position);
    }
}
