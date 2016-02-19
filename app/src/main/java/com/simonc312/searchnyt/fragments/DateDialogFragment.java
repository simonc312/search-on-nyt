package com.simonc312.searchnyt.fragments;

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
public class DateDialogFragment extends DialogFragment implements DatePicker.OnDateChangedListener {

    private static final String DATE_EXTRA = "date_extra";
    private static final String TITLE_EXTRA = "title_extra";
    @Bind(R.id.dp_date) DatePicker datePicker;
    private FilterListener mListener;
    private Calendar calendar;

    public static DateDialogFragment newInstance(int titleId){
        DateDialogFragment fragment = new DateDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_EXTRA, titleId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_date, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        final int title = getArguments().getInt(TITLE_EXTRA,R.string.pick_date);
        String beginDate = getArguments().getString(DATE_EXTRA);
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
                String beginDate = getSelectedDate(calendar);
                if (beginDate != null && !beginDate.isEmpty()) {
                    mListener.onUpdate(beginDate, title);
                    getDialog().dismiss();
                }

            }
        });
    }


   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (FilterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement " + FilterListener.class.getSimpleName());
        }
    }*/

    @Override
    public void onDetach(){
        mListener = null;
        super.onDetach();
    }

    private void initializeDatePicker(String date) {
        datePicker.setMinDate(getMinDate());
        datePicker.requestFocus();
        int year = 2016;
        int month = 1;
        int day = 10;
        if(date != null && !date.isEmpty()) {
            Date initialDate = getFilterParsedDate(date);
            if(initialDate != null) {
                calendar.setTime(initialDate);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            }
        }
        datePicker.init(year, month, day, this);
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
        calendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);
    }

    public DateDialogFragment setListener(FilterListener listener) {
        mListener = listener;
        return this;
    }

    public interface FilterListener {
        void onUpdate(String date, int titleid);
    }
}
