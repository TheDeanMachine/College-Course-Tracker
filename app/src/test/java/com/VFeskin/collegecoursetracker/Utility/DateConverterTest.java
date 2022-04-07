package com.VFeskin.collegecoursetracker.Utility;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;

public class DateConverterTest {
    Date testDate;
    Long testLong;
    Calendar calendar = Calendar.getInstance();;


    @Before
    public void setUp() throws Exception {
        calendar.set(2022, 0, 1, 10, 30);
        testDate = calendar.getTime();
    }

    @Test
    public void fromTimestamp() {

    }

    @Test
    public void toTimestamp() {

        testLong = DateConverter.fromDateToLong(testDate);

        assertEquals(DateConverter.fromLongToDate(testLong), testDate);

    }
}