package com.VFeskin.collegecoursetracker.Utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is used to combine date and time values into one date value.
 * The use of Date.setTime() resulted in unstable performance, making this class necessary.
 */
public class DateTimeParser {
    private static String startDateString;
    private static String startTimeString;
    private static String combineStartDateTimeString;
    private static SimpleDateFormat formatter = new SimpleDateFormat();

    public static Date parseDateTime(Date date, Long time ) {
        startDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
        startTimeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(time);
        combineStartDateTimeString = startDateString + " " + startTimeString;

        Date dateTime = null;
        try {
            dateTime = formatter.parse(combineStartDateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTime;
    }
}
