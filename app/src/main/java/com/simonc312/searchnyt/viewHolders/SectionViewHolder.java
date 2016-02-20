package com.simonc312.searchnyt.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;

import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.interfaces.SectionInteractionListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Simon on 2/20/2016.
 */
public class SectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @Bind(R.id.ctv_item)
    CheckedTextView checkedTextView;
    private SectionInteractionListener listener;

    public SectionViewHolder(View itemView, SectionInteractionListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        this.listener = listener;
    }

    public void setText(String text) {
        checkedTextView.setText(text);
    }

    public void setChecked(boolean isChecked) {
        checkedTextView.setChecked(isChecked);
    }

    @Override
    public void onClick(View v) {
        getAdapterPosition();
        listener.updateSection(getAdapterPosition(), !checkedTextView.isChecked());
    }
}
