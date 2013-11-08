package org.joesoft.timetogohomelogic;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class WeekRecordTest {
    public static final HoursAndMinutes ZERO_WORKHOURS = new HoursAndMinutes(0, 0);
    public static final int DUMMY_WEEKNUMBER = 1;
    private WeekRecord weekRecord;
    private PropertyReader propertyReader;
    
    @Before
    public void setUp() throws IOException {
        weekRecord = new WeekRecord(DUMMY_WEEKNUMBER);
        propertyReader = new TestPropertyReader();
    }

    @Test
    public void testCreateNewWeekShouldBeEmpty() {
        assertEquals(0, weekRecord.getNumberOfRecords());
    }
    
    @Test
    public void testAddAWorkDayWorkHoursSholdEqualToSetInIt() throws Exception {
        WorkDayRecord dayRecord = getWorkDayRecord();
        weekRecord.addWorkDayRecord(dayRecord);
        assertEquals(1, weekRecord.getNumberOfRecords());
        assertEquals(ZERO_WORKHOURS, weekRecord.getTotalWorkTime());
    }
    
    @Test
    public void testAddSomeWorkhoursShoudEqualsToTotal() throws Exception {
        WorkDayRecord dayRecord = getWorkDayRecord();
        dayRecord.setArrival(9, 15);
        dayRecord.setLunchStart(12, 00);
        dayRecord.setLunchEnds(13, 00);
        dayRecord.setLeave(16, 00);
        weekRecord.addWorkDayRecord(dayRecord);
        
        assertEquals(1, weekRecord.getNumberOfRecords());
        assertEquals(new HoursAndMinutes(5, 45), weekRecord.getTotalWorkTime());
    }
    
    @Test(expected = MaximumDaysPerWeekExceededException.class)
    public void testExceedSevenDaysShouldThrowException() throws Exception {
        WorkDayRecord workDayRecord = getWorkDayRecord();
        for (int i = 0; i < 5; i++) {
            weekRecord.addWorkDayRecord(workDayRecord);
        }
        FreeDayRecord freeDayRecord = new FreeDayRecord(30);
        for (int j = 0; j < 3; j++) {
            weekRecord.addFreeDayRecord(freeDayRecord);
        }
    }
    
    @Test
    public void testAverageHours() throws Exception {
        WorkDayRecord fiveHours = getWorkintHours(5);
        WorkDayRecord sixHours = getWorkintHours(6);
        weekRecord.addWorkDayRecord(fiveHours);
        weekRecord.addWorkDayRecord(sixHours);
        
        HoursAndMinutes avarage = weekRecord.getAvarageWorkingHours();
        assertEquals(new HoursAndMinutes(5, 30), avarage);
    }
    
    @Test
    public void testCalculateWorktimeLeft() throws Exception {
        WorkDayRecord fiveHours = getWorkintHours(5);
        WorkDayRecord sixHours = getWorkintHours(6);
        weekRecord.addWorkDayRecord(fiveHours);
        weekRecord.addWorkDayRecord(sixHours);
        
        HoursAndMinutes avarage = weekRecord.getWorkhoursLeft();
    }

    private WorkDayRecord getWorkintHours(int hours) {
        WorkDayRecord record = getWorkDayRecord();
        record.setArrival(9, 0);
        record.setLunchStart(12, 0);
        record.setLunchEnds(13, 0);
        record.setLeave(13+(hours-3), 0);
        
        return record;
    }

    private WorkDayRecord getWorkDayRecord() {
        HoursAndMinutes arrival = new HoursAndMinutes(8, 10);
        return new WorkDayRecord(31, propertyReader);
    }
}
