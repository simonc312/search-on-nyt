package com.simonc312.searchnyt.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.simonc312.searchnyt.adapters.TrendingAdapter;
import com.simonc312.searchnyt.helpers.ImageLoaderHelper;
import com.simonc312.searchnyt.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Simon on 1/28/2016.
 */
public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @Bind(R.id.iv_item)
    ImageView iv_item;
    private final TrendingAdapter.PostItemListener listener;

    public GridViewHolder(View itemView,TrendingAdapter.PostItemListener listener){
        super(itemView);
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(this);
        this.listener = listener;
    }

    public void setPostImage(String src) {
        setImageHelper(src, iv_item,R.color.placeholder_color);
    }

    protected void setImageHelper(String src,ImageView image, int placeholder){
        ImageLoaderHelper.loadWithPlaceholder(image.getContext(), src, image, placeholder,false);
    }

    protected void setImageHelperCenterCropFit(String src,ImageView image, int placeholder){
        ImageLoaderHelper.loadWithPlaceholder(image.getContext(),src,image,placeholder,true);
    }

    @Override
    public void onClick(View v) {
        listener.onPostClick(getAdapterPosition());
    }
}
