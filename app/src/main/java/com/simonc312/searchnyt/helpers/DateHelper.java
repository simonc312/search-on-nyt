package com.simonc312.searchnyt.helpers;

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
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    private static SimpleDateFormat displayDateFormat = new SimpleDateFormat("MMM d");
    private static SimpleDateFormat displayOldDateFormat = new SimpleDateFormat("MMM d, yyyy");
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

    public String getRelativeTime(String date) {
        try {
            Date date1 = dateFormat.parse(date);
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH,1);
            calendar2.setTime(new Date());
            boolean lessThanAYearAgo = calendar2.get(Calendar.YEAR) <= calendar.get(Calendar.YEAR);
            return lessThanAYearAgo ?  displayDateFormat.format(calendar.getTime()) : displayOldDateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



}
