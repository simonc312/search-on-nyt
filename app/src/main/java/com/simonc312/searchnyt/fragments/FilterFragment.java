package com.simonc312.searchnyt.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.helpers.DateHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link InteractionListener}
 * interface.
 */
public class FilterFragment extends Fragment implements DateDialogFragment.FilterListener{
    public static int FILTER_TYPE = -321;
    @Bind(R.id.tv_beginDate)
    TextView tv_beginDate;
    @Bind(R.id.tv_endDate)
    TextView tv_endDate;

    private String beginDate;
    private String endDate;

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
        DateDialogFragment.newInstance(endDate,R.string.pick_end_date)
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
        mListener.onApplyFilter(beginDate, endDate, false);

    }

    @OnClick(R.id.btn_save)
    public void handleSaveClick(View view){
        mListener.onApplyFilter(beginDate, endDate, true);
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
        mListener.onApplyFilter(beginDate,endDate,false);
    }

    private String getRelativeTime(String time){
        return DateHelper.getInstance().getRelativeFilterDate(time);
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
         * @param finishFiltering use to determine if done filtering and okay to start search.
         */
        void onApplyFilter(String beginDate, String endDate, boolean finishFiltering);
    }
}
