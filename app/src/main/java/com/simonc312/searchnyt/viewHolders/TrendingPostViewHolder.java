package com.simonc312.searchnyt.viewHolders;

import android.view.View;
import android.widget.TextView;


import com.simonc312.searchnyt.adapters.TrendingAdapter;
import com.simonc312.searchnyt.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Simon on 1/26/2016.
 */
public class TrendingPostViewHolder extends GridViewHolder {

    @Bind(R.id.tv_section)
    TextView tv_section;
    @Bind(R.id.tv_headline)
    TextView tv_headline;
    @Bind(R.id.tv_published_date)
    TextView publishedDate;

    public TrendingPostViewHolder(View itemView,TrendingAdapter.PostItemListener listener) {
        super(itemView,listener);
        ButterKnife.bind(this, itemView);
    }

    public void setHeadline(String headline){
        tv_headline.setText(headline);
    }

    public void setPublishedDate(String time) {
        publishedDate.setText(time);
    }

    public void setSection(String username) {
        tv_section.setText(username);
    }
    @Override
    public void setPostImage(String src){
        setImageHelper(src, iv_item, R.drawable.image_placeholder);
    }

}