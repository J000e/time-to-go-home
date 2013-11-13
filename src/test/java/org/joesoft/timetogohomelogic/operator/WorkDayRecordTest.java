package org.joesoft.timetogohomelogic.operator;

import org.joesoft.timetogohomelogic.operator.HoursAndMinutes;
import org.joesoft.timetogohomelogic.operator.WorkDayRecord;
import org.joesoft.timetogohomelogic.common.PropertyReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import org.joda.time.DateTime;
import org.joesoft.timetogohomelogic.TestPropertyReader;
import org.junit.Before;
import org.junit.Test;

public class WorkDayRecordTest {
    private WorkDayRecord record;
    private PropertyReader propertyReader;
    
    @Before
    public void setUp() {
        propertyReader = new TestPropertyReader();
        Date testDate = new DateTime(2013, 11, 12, 8, 15).toDate();
        record = new WorkDayRecord(testDate, null, propertyReader);
        record.setArrival(0, 0);
    }
    
    @Test
    public void testWorkHoursIsZero() {
        record.setLunchStart(0, 0);
        record.setLunchEnds(0, 30);
        record.setLeave(0, 30);
        
        HoursAndMinutes hoursAndMinutes = record.getTotalWorkTime();   
        assertEquals(0, hoursAndMinutes.hours);
        assertEquals(0, hoursAndMinutes.minutes);
    }
    
    @Test
    public void testLeaveIncrementsWorkHours() {
        record.setLunchStart(0, 0);
        record.setLunchEnds(0, 30);
        record.setLeave(2, 40);
        
        HoursAndMinutes hoursAndMinutes = record.getTotalWorkTime();   
        assertEquals(2, hoursAndMinutes.hours);
        assertEquals(10, hoursAndMinutes.minutes);
    }
    
    @Test
    public void testSetupAllOnlyHours() {
        record.setArrival(8, 0);
        record.setLunchStart(12, 0);
        record.setLunchEnds(13, 0);
        record.setLeave(17, 0);
        
        HoursAndMinutes ham = record.getTotalWorkTime();
        assertEquals(8, ham.hours);
        assertEquals(0, ham.minutes);
    }
    
    @Test
    public void testSetupAllHoursAndMinutes() {
        record.setArrival(8, 0);
        record.setLunchStart(12, 0);
        record.setLunchEnds(12, 30);
        record.setLeave(17, 0);
        
        HoursAndMinutes ham = record.getTotalWorkTime();
        assertEquals(8, ham.hours);
        assertEquals(30, ham.minutes);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testLunchStartCantHappenBeforeArrival() {
        record.setArrival(8, 0);
        record.setLunchStart(7, 59);
    }
    
    @Test
    public void testIfLunchTimeIsShorterThanMinimumModifyLunchEndAccording() {
        record.setArrival(8, 0);
        record.setLunchStart(12, 00);
        record.setLunchEnds(12, 10);
        
        assertEquals(12, record.getLunchEnds().hours);
        assertEquals(30, record.getLunchEnds().minutes);
    }
    
    @Test
    public void testIdealLeaveTimeWhenAllTimesAreSet() {
        record.setLunchStart(0, 0);
        record.setLunchEnds(0, 30);
        record.setLeave(0, 30);
        
        assertEquals(8, record.getIdealLeaveTime().hours);
        assertEquals(30, record.getIdealLeaveTime().minutes);
    }
    
    @Test
    public void testIdealLeaveTimeWhenAllTimesAreSetWithLongLunch() {
        record.setLunchStart(0, 0);
        record.setLunchEnds(1, 30);
        record.setLeave(1, 30);
        
        assertEquals(9, record.getIdealLeaveTime().hours);
        assertEquals(30, record.getIdealLeaveTime().minutes);
    }
    
    @Test
    public void testIdealLeaveTimeWhenOnlyArrivalIsSet() {
        assertEquals(8, record.getIdealLeaveTime().hours);
        assertEquals(30, record.getIdealLeaveTime().minutes);
    }
    
    @Test
    public void testIdealLeaveTimeWheArrivalAndLunchStartIsSet() {
        assertEquals(8, record.getIdealLeaveTime().hours);
        assertEquals(30, record.getIdealLeaveTime().minutes);
    }
    
    @Test
    public void testTimeWorkedAfterArrivalBeforeLunchStart() {
        record.setArrival(8, 0);
        HoursAndMinutes actualTime = new HoursAndMinutes(9, 15);

        HoursAndMinutes timeWorked = record.calculateTimeWorked(actualTime);
        
        assertEquals(1, timeWorked.hours);
        assertEquals(15, timeWorked.minutes);
    }
    
    @Test
    public void testTimeWorkedAfterLunchStartBeforeLunchEnds() {
        record.setArrival(8, 0);
        record.setLunchStart(12, 10);
        HoursAndMinutes actualTime = new HoursAndMinutes(12, 30);
        
        HoursAndMinutes timeWorked = record.calculateTimeWorked(actualTime);
        
        assertEquals(4, timeWorked.hours);
        assertEquals(10, timeWorked.minutes);
    }
    
    @Test
    public void testTimeWorkedAfterLunchEndsBeforeLeave() {
        record.setArrival(8, 0);
        record.setLunchStart(12, 10);
        record.setLunchEnds(12, 40);
        HoursAndMinutes actualTime = new HoursAndMinutes(14, 20);
        
        HoursAndMinutes timeWorked = record.calculateTimeWorked(actualTime);
        
        assertEquals(5, timeWorked.hours);
        assertEquals(50, timeWorked.minutes);
    }
    
    @Test
    public void testTimeWorkedAfterLeave() {
        record.setArrival(8, 0); 
        record.setLunchStart(12, 10);
        record.setLunchEnds(12, 40);
        record.setLeave(17, 22); //
        HoursAndMinutes actualTime = new HoursAndMinutes(17, 50);
        
        HoursAndMinutes timeWorked = record.calculateTimeWorked(actualTime);
        
        assertEquals(8, timeWorked.hours);
        assertEquals(52, timeWorked.minutes);
    }
    
    @Test
    public void testActualTimeIsBeforeArrival() {
        record.setArrival(8, 0);
        HoursAndMinutes actualTime = new HoursAndMinutes(7, 15);
        
        HoursAndMinutes timeWorked = record.calculateTimeWorked(actualTime);
        
        assertEquals(0, timeWorked.hours);
        assertEquals(0, timeWorked.minutes);
    }
    
    @Test
    public void testActualTimeIsBeforeLunchStart() {
        record.setArrival(8, 0);
        record.setLunchStart(12, 10);
        HoursAndMinutes actualTime = new HoursAndMinutes(10, 15);
        
        HoursAndMinutes timeWorked = record.calculateTimeWorked(actualTime);
        
        assertEquals(2, timeWorked.hours);
        assertEquals(15, timeWorked.minutes);
    }
    
    @Test
    public void testActualTimeIsBeforeLunchEnd() {
        record.setArrival(8, 0);
        record.setLunchStart(12, 10);
        record.setLunchEnds(13, 30);
        HoursAndMinutes actualTime = new HoursAndMinutes(13, 00);
        
        HoursAndMinutes timeWorked = record.calculateTimeWorked(actualTime);
        
        assertEquals(4, timeWorked.hours);
        assertEquals(10, timeWorked.minutes);
    }
    
    @Test
    public void testActualTimeIsBeforeLeave() {
        record.setArrival(8, 0);
        record.setLunchStart(12, 10);
        record.setLunchEnds(13, 30);
        record.setLeave(18, 45);
        HoursAndMinutes actualTime = new HoursAndMinutes(17, 45);
        
        HoursAndMinutes timeWorked = record.calculateTimeWorked(actualTime);
        
        assertEquals(8, timeWorked.hours);
        assertEquals(25, timeWorked.minutes);
    }
    
    @Test
    public void theActualDayIsTheSameAsTheVerifiedOne() {
        Date actualDate = new DateTime(2013, 11, 12, 8, 0).toDate();
        assertTrue(record.isToday(actualDate));
    }
    
    @Test
    public void theActualDayIsNotTheSameAsTheVerifiedOne() {
        Date actualDate = new DateTime(2013, 11, 13, 8, 0).toDate();
        assertFalse(record.isToday(actualDate));
    }
    
}
