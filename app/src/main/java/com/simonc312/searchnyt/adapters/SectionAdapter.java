package com.simonc312.searchnyt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.interfaces.SectionInteractionListener;
import com.simonc312.searchnyt.models.Section;
import com.simonc312.searchnyt.viewHolders.SectionViewHolder;

import java.util.List;

/**
 * Created by Simon on 2/19/2016.
 */
public class SectionAdapter extends RecyclerView.Adapter<SectionViewHolder> implements SectionInteractionListener {

    private final Context mContext;
    private List<Section> sections;

    public SectionAdapter(Context context, List<Section> sections){
        this.mContext = context;
        this.sections = sections;
    }

    public List<Section> getSections(){
        return sections;
    }

    public void uncheckSections() {
        for(Section section: sections){
            section.setIsChecked(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public void updateSection(int adapterPosition, boolean checked) {
        sections.get(adapterPosition).setIsChecked(checked);
        notifyItemChanged(adapterPosition);
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.expand_group_row, parent, false);
        return new SectionViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        Section section = sections.get(position);
        holder.setText(section.getSection());
        holder.setChecked(section.isChecked());
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }
}
