package org.joesoft.timetogohomelogic.operator;

import org.joesoft.timetogohomelogic.operator.HoursAndMinutes;
import org.joesoft.timetogohomelogic.operator.WeekRecord;
import org.joesoft.timetogohomelogic.operator.MaximumDaysPerWeekExceededException;
import org.joesoft.timetogohomelogic.operator.WorkDayRecord;
import org.joesoft.timetogohomelogic.operator.FreeDayRecord;
import org.joesoft.timetogohomelogic.common.PropertyReader;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;
import org.joda.time.DateTime;
import org.joesoft.timetogohomelogic.TestPropertyReader;
import org.junit.Before;
import org.junit.Test;

public class WeekRecordTest {
    private static final HoursAndMinutes ZERO_WORKHOURS = new HoursAndMinutes(0, 0);
    private static final int DUMMY_WEEKNUMBER = 1;
    private static final Date testDate = new DateTime(2013, 11, 12, 8, 15).toDate();
    private static final Date otherDate = new DateTime(2013, 11, 13, 12, 15).toDate();
    private WeekRecord weekRecord;
    private PropertyReader propertyReader;
    
    @Before
    public void setUp() throws IOException {
        propertyReader = new TestPropertyReader();
        weekRecord = new WeekRecord(DUMMY_WEEKNUMBER, propertyReader);
    }

    @Test
    public void testCreateNewWeekShouldBeEmpty() {
        assertEquals(0, weekRecord.getNumberOfRecords());
    }
    
    @Test
    public void testAddAWorkDayWorkHoursSholdEqualToSetInIt() throws Exception {
        WorkDayRecord dayRecord = getWorkDayRecord(testDate);
        weekRecord.addWorkDayRecord(dayRecord);
        assertEquals(1, weekRecord.getNumberOfRecords());
        assertEquals(ZERO_WORKHOURS, weekRecord.getTotalWorkTime(otherDate));
    }
    
    @Test
    public void testAddSomeWorkhoursShoudEqualsToTotal() throws Exception {
        WorkDayRecord dayRecord = getWorkDayRecord(testDate);
        dayRecord.setArrival(9, 15);
        dayRecord.setLunchStart(12, 00);
        dayRecord.setLunchEnds(13, 00);
        dayRecord.setLeave(16, 00);
        weekRecord.addWorkDayRecord(dayRecord);
        
        assertEquals(1, weekRecord.getNumberOfRecords());
        assertEquals(new HoursAndMinutes(5, 45), weekRecord.getTotalWorkTime(otherDate));
    }
    
    @Test(expected = MaximumDaysPerWeekExceededException.class)
    public void testExceedSevenDaysShouldThrowException() throws Exception {
        WorkDayRecord workDayRecord = getWorkDayRecord(testDate);
        for (int i = 0; i < 5; i++) {
            weekRecord.addWorkDayRecord(workDayRecord);
        }
        FreeDayRecord freeDayRecord = new FreeDayRecord(testDate);
        for (int j = 0; j < 3; j++) {
            weekRecord.addFreeDayRecord(freeDayRecord);
        }
    }
    
    @Test
    public void twoZeroWorkRecordsAvarageShouldBeZeroAsWell() throws Exception {
        weekRecord.addWorkDayRecord(getZeroWorkintHours(testDate));
        weekRecord.addWorkDayRecord(getZeroWorkintHours(testDate));
        
        HoursAndMinutes avarage = weekRecord.getAvarageWorkingHours(otherDate);
        assertEquals(ZERO_WORKHOURS, avarage);
    }
    
    @Test
    public void totalWorkTimeWhenFirstDayIsDoneAndTheSecondIsTheActual() {
        weekRecord.addWorkDayRecord(getWorkintHours(5, testDate));
        weekRecord.addWorkDayRecord(getWorkintHours(5, otherDate));
        
        HoursAndMinutes avarage = weekRecord.getAvarageWorkingHours(otherDate);
        assertEquals(new HoursAndMinutes(4, 0), avarage);
    }
    
    @Test
    public void testAverageHours() throws Exception {
        WorkDayRecord fiveHours = getWorkintHours(5, testDate);
        WorkDayRecord sixHours = getWorkintHours(6, testDate);
        weekRecord.addWorkDayRecord(fiveHours);
        weekRecord.addWorkDayRecord(sixHours);
        
        HoursAndMinutes avarage = weekRecord.getAvarageWorkingHours(otherDate);
        assertEquals(new HoursAndMinutes(5, 30), avarage);
    }
    
    @Test
    public void hoursLeftShouldRetrunZeroIfAllWorkhoursDone() throws Exception {
        Date actualTime = new DateTime(2013, 11, 11, 14, 33).toDate();
        weekRecord.addWorkDayRecord(getWorkintHours(8, testDate));
        weekRecord.addWorkDayRecord(getWorkintHours(8, testDate));
        weekRecord.addWorkDayRecord(getWorkintHours(8, testDate));
        weekRecord.addWorkDayRecord(getWorkintHours(8, testDate));
        weekRecord.addWorkDayRecord(getWorkintHours(8, testDate));
        HoursAndMinutes hoursLeft = weekRecord.getHoursLeft(5, 0, actualTime);
        
        assertEquals(new HoursAndMinutes(), hoursLeft);
    }
    
    @Test
    public void threeDaysDone() {
        Date actualTime = new DateTime(2013, 11, 11, 14, 33).toDate();
        weekRecord.addWorkDayRecord(getWorkintHours(8, testDate));
        weekRecord.addWorkDayRecord(getWorkintHours(8, testDate));
        weekRecord.addWorkDayRecord(getWorkintHours(8, testDate));
        
        HoursAndMinutes hoursLeft = weekRecord.getHoursLeft(5, 2, actualTime);
        assertEquals(new HoursAndMinutes(8, 0), hoursLeft);
    }
    
    @Test
    public void noRecordInTheWeek() {
        Date actualTime = new DateTime(2013, 11, 11, 14, 33).toDate();
        HoursAndMinutes hoursLeft = weekRecord.getHoursLeft(5, 1, actualTime);
        
        assertEquals(new HoursAndMinutes(40, 0), hoursLeft);
    }

    private WorkDayRecord getWorkintHours(int hours, Date date) {
        WorkDayRecord record = getWorkDayRecord(date);
        record.setArrival(9, 0);
        record.setLunchStart(12, 0);
        record.setLunchEnds(13, 0);
        record.setLeave(13+(hours-3), 0);
        
        return record;
    }

    private WorkDayRecord getZeroWorkintHours(Date date) {
        WorkDayRecord record = getWorkDayRecord(date);
        record.setArrival(9, 0);
        record.setLunchStart(9, 0);
        record.setLunchEnds(9, 30);
        record.setLeave(9, 30);
        
        return record;
    }

    private WorkDayRecord getWorkDayRecord(Date date) {
        return new WorkDayRecord(date, null, propertyReader);
    }
}
