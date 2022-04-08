package com.VFeskin.collegecoursetracker.Utility;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;

public class DateConverterTest {
    private Date calenderDate;
    private Date testDate;
    private Long calenderLong;
    private Long testLong;
    private Calendar calendar = Calendar.getInstance();

    @Before
    public void setUp() throws Exception {
        calendar.set(2022, 0, 1, 10, 30);
        calenderDate = calendar.getTime();
        calenderLong = calendar.getTimeInMillis();
    }

    @Test
    public void fromLongToDateTest() {
        testDate = DateConverter.fromLongToDate(calenderLong);
        assertEquals(calenderDate, testDate);
    }

    @Test
    public void fromDateToLongTest() {
        testLong = DateConverter.fromDateToLong(calenderDate);
        assertEquals(calenderLong, testLong);
    }
}