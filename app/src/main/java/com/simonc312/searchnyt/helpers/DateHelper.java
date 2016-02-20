package com.simonc312.searchnyt.helpers;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Simon on 2/11/2016.
 */
public class DateHelper {
    private static DateHelper instance;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat displayDateFormat = new SimpleDateFormat("MMM d");
    private static SimpleDateFormat displayOldDateFormat = new SimpleDateFormat("MMM d, yyyy");
    private static SimpleDateFormat filterDateFormat = new SimpleDateFormat("yyyyMMdd");
    private final Calendar calendar;
    private final Calendar calendar2;

    private DateHelper(){
        this.calendar = new GregorianCalendar();
        this.calendar2 = new GregorianCalendar();
    }
    public static DateHelper getInstance(){
        if(instance == null)
            instance = new DateHelper();
        return instance;
    }

    /**
     *
     * @param date should be in format {@link DateHelper#dateFormat}
     * @return
     */
    public String getRelativeTime(String date) {
        try {
            Date date1 = dateFormat.parse(date);
            return getRelativeTime(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getRelativeTime(Date date) {
        calendar.setTime(date);
        calendar2.setTime(new Date());
        boolean inTheFuture = calendar.after(calendar2);
        boolean isToday = calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                            calendar.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
        String relativeTime;
        if(inTheFuture)
            relativeTime = "Fresh off the press";
        else if(isToday)
            relativeTime = "Today";
        else
            relativeTime = DateUtils.getRelativeTimeSpanString(calendar.getTime().getTime()).toString();
        return relativeTime;
    }

    public String getFilterFormatDate(Date date) {
        return filterDateFormat.format(date);
    }

    public Date getFilterParsedDate(String date) {
        try {
            return filterDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Calendar getCalendarFromLong(long date) {
        calendar.setTime(new Date(date));
        return calendar;
    }

    /**
     *
     * @param dateString is is string in format matching {@link DateHelper#filterDateFormat}
     * @return {@link String} after converting to {@link Date} => {@link DateHelper#getRelativeTime(Date)}
     */
    public String getRelativeFilterDate(String dateString) {
        Date date = null;
        try {
            date = filterDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getRelativeTime(date);
    }
}
