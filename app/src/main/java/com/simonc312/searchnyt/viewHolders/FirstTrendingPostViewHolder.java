package com.simonc312.searchnyt.viewHolders;

import android.view.View;
import android.widget.TextView;

import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.adapters.TrendingAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Simon on 1/26/2016.
 */
public class FirstTrendingPostViewHolder extends TrendingPostViewHolder {

    @Bind(R.id.tv_caption)
    TextView tv_caption;

    public FirstTrendingPostViewHolder(View itemView, TrendingAdapter.PostItemListener listener) {
        super(itemView,listener);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setPostImage(String src){
        setImageHelper(src, iv_item, R.drawable.image_placeholder_jumbo);
    }

    public void setCaption(String caption) {
        this.tv_caption.setText(caption);
    }

}