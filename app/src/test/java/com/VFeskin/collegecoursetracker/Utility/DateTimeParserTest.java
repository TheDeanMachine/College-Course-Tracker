package com.VFeskin.collegecoursetracker.Utility;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Calendar;
import java.util.Date;

public class DateTimeParserTest {

    private Date startDate;
    private Long startTime;
    private Date startDateTime;
    private final Calendar calendar = Calendar.getInstance();

    @Before
    public void setUp() throws Exception {
        calendar.set(2022, 0, 1, 10, 30);
        // assign value
        startDate = calendar.getTime();
        startTime = calendar.getTimeInMillis();
    }

    @Test
    public void parseDateTimeTest() {
        // convert to single value
        startDateTime = DateTimeParser.parseDateTime(startDate, startTime); // returns null due to .parse() method

        // test values to see if they still match original values after conversion
        assertEquals(startDate, startDateTime);
        assertEquals(startTime, DateConverter.fromDateToLong(startDateTime));

    }
}