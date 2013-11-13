package org.joesoft.timetogohomelogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class HoursAndMinutesTest {
    private static final HoursAndMinutes ONE_OCLOCK = new HoursAndMinutes(1, 0);
    private HoursAndMinutes underTest;
    
    @Before
    public void setUp() {
        underTest = new HoursAndMinutes(2, 20);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullInputShouldCauseException() throws Exception {
        underTest.minus(null);
    }
    
    @Test
    public void testMinusOneHour() throws Exception {
        HoursAndMinutes modified = underTest.minus(ONE_OCLOCK);
        
        assertTime(1, 20, modified);
    }
    
    @Test
    public void testMinusTenMinutesNoHourChange() throws Exception {
        HoursAndMinutes modified = underTest.minus(new HoursAndMinutes(0, 10));
        
        assertTime(2, 10, modified);        
    }
    
    @Test
    public void testMinusTenMinutesWhenHourChange() throws Exception {
        HoursAndMinutes modified = underTest.minus(new HoursAndMinutes(1, 30));
        
        assertTime(0, 50, modified);        
    }
    
    @Test(expected = HoursAndMinutes.TimeCantBeNegativeException.class)
    public void testTimeCantBeNegative() throws Exception {
        underTest.minus(new HoursAndMinutes(2, 30));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullImputShoudCauseException() throws Exception {
        underTest.plus(null);
    }
    
    @Test
    public void testAddZeroTimeShouldCauseNoChange() throws Exception {
        HoursAndMinutes modified = underTest.plus(new HoursAndMinutes(0, 0));
        
        assertTime(2, 20, modified);
    }
    
    @Test
    public void testAddAnHour() throws Exception {
        HoursAndMinutes modified = underTest.plus(ONE_OCLOCK);
        
        assertTime(3, 20, modified);
    }
    
    @Test
    public void testAddTenMinutes() throws Exception {
        HoursAndMinutes modified = underTest.plus(new HoursAndMinutes(0, 10));
        
        assertTime(2, 30, modified);
    }
    
    @Test
    public void testMinutesOverLoading() throws Exception {
        HoursAndMinutes modified = underTest.plus(new HoursAndMinutes(0, 50));
        
        assertTime(3, 10, modified);
    }
    
    @Test
    public void testDivide() throws Exception {
        HoursAndMinutes modified = underTest.divide(2);
        
        assertTime(1, 10, modified);
    }
    
    @Test
    public void testPlusMinutesLessThanSixty() throws Exception {
        HoursAndMinutes modified = underTest.plusMinutes(30);
        
        assertTime(2, 50, modified);
    }
    
    @Test
    public void testPlusMinutesMinuteOverflow() throws Exception {
        HoursAndMinutes modified = underTest.plusMinutes(50);
        
        assertTime(3, 10, modified);
    }
    
    @Test
    public void TwoTwentyIsGreaterThanOneOClock() throws Exception {
        assertTrue(underTest.isGreaterThan(ONE_OCLOCK));
    }
    
    @Test
    public void testOneOClockIsLessThanTwoTwenty() throws Exception {
        assertFalse(ONE_OCLOCK.isGreaterThan(underTest));
    }
    
    @Test
    public void multiplyByZero() {
        HoursAndMinutes modified = underTest.multiply(0);
        assertTime(0, 0, modified);
    }
    
    @Test
    public void multiplyByOne() {
        HoursAndMinutes modified = underTest.multiply(1);
        assertTime(2, 20, modified);
    }
    
    @Test
    public void multiplyByThree() {
        HoursAndMinutes modified = underTest.multiply(3);
        assertTime(7, 0, modified);
    }
    
    private void assertTime(int newHour, int newMinutes, HoursAndMinutes modified) {
        assertEquals(newHour, modified.hours);
        assertEquals(newMinutes, modified.minutes);
    }

}
