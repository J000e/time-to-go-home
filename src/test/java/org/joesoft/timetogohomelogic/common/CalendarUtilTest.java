package org.joesoft.timetogohomelogic.common;

import org.joesoft.timetogohomelogic.common.CalendarUtil;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class CalendarUtilTest {

    private CalendarUtil calendarUtil;

    @Before
    public void setUp() {
        calendarUtil = new CalendarUtil();
    }

    @Test
    public void testGetFromDate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2013, 05, 11, 18, 30);
        Date date = calendar.getTime();

        assertEquals(2013, calendarUtil.getFromDate(Calendar.YEAR, date));
        assertEquals(05, calendarUtil.getFromDate(Calendar.MONTH, date));
        assertEquals(11, calendarUtil.getFromDate(Calendar.DAY_OF_MONTH, date));
        assertEquals(18, calendarUtil.getFromDate(Calendar.HOUR_OF_DAY, date));
        assertEquals(30, calendarUtil.getFromDate(Calendar.MINUTE, date));
    }

    @Test
    public void getNumberOfWorkdaysThisYearNovemberAndDecember() throws Exception {
        assertEquals(21, calendarUtil.getNumberOfWorkDays(2013, Calendar.NOVEMBER));
        assertEquals(22, calendarUtil.getNumberOfWorkDays(2013, Calendar.DECEMBER));
        assertEquals(23, calendarUtil.getNumberOfWorkDays(2014, Calendar.JANUARY));
        assertEquals(20, calendarUtil.getNumberOfWorkDays(2014, Calendar.FEBRUARY));
        assertEquals(21, calendarUtil.getNumberOfWorkDays(2014, Calendar.MARCH));
        assertEquals(22, calendarUtil.getNumberOfWorkDays(2014, Calendar.APRIL));
    }

}
