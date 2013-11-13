package org.joesoft.timetogohomelogic.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joesoft.timetogohomelogic.TestPropertyReader;
import org.joesoft.timetogohomelogic.operator.WeekRecord;
import org.junit.Before;
import org.junit.Test;

public class WeekRecordProviderTest {

    private static final CalendarUtil calendarUtil = new CalendarUtil();
    private WeekRecordProvider underTest;
    private PropertyReader propertyReader;
    private TestWeekDao testWeekDao;
    private int weekNumber;
    private Date actualDate;
    private WeekRecord testRecord;

    @Before
    public void setUp() {
        testWeekDao = new TestWeekDao();
        underTest = new WeekRecordProvider(testWeekDao);
        propertyReader = new TestPropertyReader();
        actualDate = new DateTime(2013, 11, 13, 15, 12).toDate();
        weekNumber = calendarUtil.getFromDate(Calendar.WEEK_OF_YEAR, actualDate);
        testRecord = new WeekRecord(weekNumber, propertyReader);
    }

    @Test
    public void accordingADateGetAnExistingWeekRecord() {
        testWeekDao.addRecord(testRecord);
        
        WeekRecord record = underTest.getByDate(actualDate, propertyReader);

        assertEquals(testRecord, record);
    }
    
    @Test
    public void getANewRecordIfNoRecordExistsWithTheGivenNumber() {
        testWeekDao.addRecord(testRecord);
        Date otherDate = new DateTime(2013, 11, 1, 8, 10).toDate();
        int otherWeekNumber = calendarUtil.getFromDate(Calendar.WEEK_OF_YEAR, otherDate);
        
        WeekRecord record = underTest.getByDate(otherDate, propertyReader);
        
        assertFalse(testRecord.equals(record));
        assertEquals(otherWeekNumber, record.getWeekNumber());
    }

}
