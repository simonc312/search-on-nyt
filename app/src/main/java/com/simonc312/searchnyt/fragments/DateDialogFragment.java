package com.simonc312.searchnyt.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

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
    @Bind(R.id.btn_default)
    TextView btnCancel;
    @Bind(R.id.btn_save)
    TextView btnSave;
    private FilterListener mListener;
    private Calendar calendar;
    private Date minDate;

    public static DateDialogFragment newInstance(String date, int titleId){
        DateDialogFragment fragment = new DateDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DATE_EXTRA, date);
        bundle.putInt(TITLE_EXTRA, titleId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(calendar == null)
            calendar = new GregorianCalendar();
        if(minDate == null)
            setMinDate(getString(R.string.earliestSearchBeginDate));
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
        final String beginDate = getArguments().getString(DATE_EXTRA);
        getDialog().setTitle(title);

        initDatePicker(beginDate);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = getSelectedDate(calendar);
                if (date != null && !date.isEmpty()) {
                    mListener.onUpdate(date, title);
                    getDialog().dismiss();
                }

            }
        });
    }

    @Override
    public void onDetach(){
        mListener = null;
        super.onDetach();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    public DateDialogFragment setListener(FilterListener listener) {
        mListener = listener;
        return this;
    }

    public DateDialogFragment setMinDate(String date) {
        if(date != null && !date.isEmpty())
            this.minDate = getFilterParsedDate(date);
        return this;
    }

    private void initDatePicker(String date) {
        long initialDateMilli = getMaxDate();
        datePicker.setMinDate(getMinDate());
        datePicker.setMaxDate(initialDateMilli);
        datePicker.requestFocus();
        Calendar initialCalendar;
        if(date != null && !date.isEmpty()) {
            Date initialDate = getFilterParsedDate(date);
            calendar.setTime(initialDate);
            initialCalendar = calendar;
        } else{
            initialCalendar = DateHelper.getInstance().getCalendarFromLong(initialDateMilli);
        }
        initDateWithCalendar(initialCalendar);
    }

    private void initDateWithCalendar(Calendar calendar){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, this);
    }

    private Date getFilterParsedDate(String date){
        return DateHelper.getInstance().getFilterParsedDate(date);
    }

    private String getSelectedDate(Calendar calendar){
        return DateHelper.getInstance().getFilterFormatDate(calendar.getTime());
    }

    /**
     * Returns today's date
     * @return
     */
    private long getMaxDate(){
        return new Date().getTime();
    }

    private long getMinDate(){
        return minDate.getTime();
    }

    public interface FilterListener {
        void onUpdate(String date, int titleId);
    }
}
