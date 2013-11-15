package org.joesoft.timetogohomelogic.operator;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.joda.time.DateTime;
import org.joesoft.timetogohomelogic.TestPropertyReader;
import org.joesoft.timetogohomelogic.common.CalendarUtil;
import org.joesoft.timetogohomelogic.common.PropertyReader;
import org.junit.Before;
import org.junit.Test;

public class TimeSelectorTest {
    private TimeSelector underTest;
    private Date firstDate;
    private Date secondDate;
    
    @Before
    public void setUp() {
        firstDate = new DateTime(2013, 8, 14, 8, 10).toDate();
        secondDate = new DateTime(2013, 11, 14, 8, 10).toDate();
        
        Set<DayRecord> records = new HashSet<DayRecord>();
        PropertyReader propertyReader = new TestPropertyReader();
        records.add(new WorkDayRecord(firstDate, propertyReader));
        records.add(new WorkDayRecord(secondDate, propertyReader));
        
        underTest = new TimeSelector(records);
    }
    
    @Test
    public void giveBackDayRecordsAccordingWeekNumber() {
        CalendarUtil calendarUtil = new CalendarUtil();
        int firstWeekNumber = calendarUtil.getFromDate(Calendar.WEEK_OF_YEAR, firstDate);
        int secondWeekNumber = calendarUtil.getFromDate(Calendar.WEEK_OF_YEAR, secondDate);
        
        assertEquals(1, underTest.getRecordsByWeekNumber(firstWeekNumber).size());
    }
}
