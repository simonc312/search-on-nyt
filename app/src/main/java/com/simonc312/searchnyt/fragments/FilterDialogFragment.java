package com.simonc312.searchnyt.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.simonc312.searchnyt.R;
import com.simonc312.searchnyt.helpers.DateHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Simon on 2/18/2016.
 */
public class FilterDialogFragment extends DialogFragment implements DatePicker.OnDateChangedListener {

    private static final String BEGIN_DATE_EXTRA = "begin_date_extra";
    private static final String END_DATE_EXTRA = "end_date_extra";
    private static final String SECTIONS_EXTRA = "sections_extra";
    private static final String TITLE_EXTRA = "title_extra";
    @Bind(R.id.dp_begin_date) DatePicker datePicker;
    private FilterListener mListener;
    private Calendar beginCalendar;
    private Calendar endCalendar;

    public static FilterDialogFragment newInstance(){
        FilterDialogFragment fragment = new FilterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_EXTRA, R.string.filter_title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_dialog, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        int title = getArguments().getInt(TITLE_EXTRA);
        String beginDate = getArguments().getString(BEGIN_DATE_EXTRA);
        getDialog().setTitle(title);

        initializeDatePicker(beginDate);

        final Button cancelButton = (Button) view.findViewById(R.id.btn_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        final Button saveButton = (Button) view.findViewById(R.id.btn_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String beginDate = getSelectedDate(beginCalendar);
                if (beginDate != null && !beginDate.isEmpty()) {
                    mListener.onUpdate(beginDate);
                    getDialog().dismiss();
                }

            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            mListener = (FilterListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement " + FilterListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach(){
        mListener = null;
        super.onDetach();
    }

    private void initializeDatePicker(String beginDate) {
        datePicker.setMinDate(getMinDate());
        datePicker.requestFocus();
        int year = 2016;
        int month = 1;
        int day = 10;
        if(beginDate != null && !beginDate.isEmpty()) {
            Date initialDate = getFilterParsedDate(beginDate);
            if(initialDate != null) {
                beginCalendar.setTime(initialDate);
                year = beginCalendar.get(Calendar.YEAR);
                month = beginCalendar.get(Calendar.MONTH);
                day = beginCalendar.get(Calendar.DAY_OF_MONTH);
            }
        }
        datePicker.init(year,month,day,this);
    }

    private Date getFilterParsedDate(String date){
        return DateHelper.getInstance().getFilterParsedDate(date);
    }

    private String getSelectedDate(Calendar calendar){
        return DateHelper.getInstance().getFilterFormatDate(calendar.getTime());
    }

    private long getMinDate(){
        return new Date().getTime();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        beginCalendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);
    }

    public interface FilterListener {
        void onUpdate(String beginDate);
    }
}
