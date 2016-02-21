package com.simonc312.searchnyt.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.adapters.SectionAdapter;
import com.simonc312.searchnyt.helpers.DateHelper;
import com.simonc312.searchnyt.helpers.GridItemDecoration;
import com.simonc312.searchnyt.models.Section;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link InteractionListener}
 * interface.
 */
public class FilterFragment extends Fragment implements DateDialogFragment.FilterListener{
    @BindInt(R.integer.grid_layout_span_count)
    int GRID_LAYOUT_SPAN_COUNT;
    @BindInt(R.integer.grid_layout_item_spacing)
    int GRID_LAYOUT_ITEM_SPACING;

    @Bind(R.id.tv_beginDate)
    TextView tv_beginDate;
    @Bind(R.id.tv_endDate)
    TextView tv_endDate;
    @Bind(R.id.rv_sections)
    RecyclerView rv_sections;
    SectionAdapter adapter;

    private String beginDate;
    private String endDate;
    private String sections;

    private InteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FilterFragment() {
    }

    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDates();
    }

    private void initializeDates() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        ButterKnife.bind(this, view);
        adapter = new SectionAdapter(getContext(), getSections());
        rv_sections.setHasFixedSize(true);
        rv_sections.setAdapter(adapter);
        rv_sections.setLayoutManager(new GridLayoutManager(getContext(), GRID_LAYOUT_SPAN_COUNT));
        rv_sections.addItemDecoration(new GridItemDecoration(GRID_LAYOUT_SPAN_COUNT,GRID_LAYOUT_ITEM_SPACING,false));
        return view;
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

    @OnClick(R.id.tv_beginDate)
    public void handleBeginDateClick(View view){
        DateDialogFragment.newInstance(beginDate,R.string.pick_start_date)
                .setListener(this)
                .show(getChildFragmentManager(), "startDate");
    }

    @OnClick(R.id.tv_endDate)
    public void handleEndDateClick(View view){
        DateDialogFragment.newInstance(endDate, R.string.pick_end_date)
                .setMinDate(beginDate)
                .setListener(this)
                .show(getChildFragmentManager(), "endDate");
    }

    @OnClick(R.id.btn_default)
    public void handleCancelClick(){
        //reset values to default
        tv_beginDate.setText(null);
        tv_endDate.setText(null);
        beginDate = null;
        endDate = null;
        sections = null;
        adapter.uncheckSections();
        mListener.onApplyFilter(beginDate, endDate, sections, false);

    }

    @OnClick(R.id.btn_save)
    public void handleSaveClick(View view){
        sections = getSelectedSectionsString(adapter.getSections());
        mListener.onApplyFilter(beginDate, endDate, sections, true);
    }

    @Override
    public void onUpdate(String date, int titleId) {
        String relativeDate = DateHelper.getInstance().getRelativeFilterDate(date);
        if(titleId == R.string.pick_start_date) {
            this.tv_beginDate.setText(relativeDate);
            this.beginDate = date;
        }
        else if(titleId == R.string.pick_end_date) {
            this.tv_endDate.setText(relativeDate);
            this.endDate = date;
        }
        mListener.onApplyFilter(beginDate,endDate,sections,false);
    }

    private List<Section> getSections() {
        String[] sections = getContext().getResources().getStringArray(R.array.sections_array);
        List<Section> sectionList = new ArrayList<>(sections.length);
        for(String section : sections){
            sectionList.add(new Section(section,false));
        }
        return sectionList;
    }

    private String getSelectedSectionsString(List<Section> sections){
        StringBuilder sb = new StringBuilder();
        for(Section section : sections){
            if(section.isChecked())
                sb.append(String.format(" \"%s\" ",section.getSection()));
        }
        return sb.toString();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface InteractionListener {
        /**
         *
         * @param beginDate
         * @param endDate
         * @param sections
         * @param finishFiltering use to determine if done filtering and okay to start search.
         */
        void onApplyFilter(String beginDate, String endDate, String sections, boolean finishFiltering);
    }
}
