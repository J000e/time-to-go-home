package org.joesoft.timetogohomelogic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Test;

public class LearningJodaTimeTest {

    @Test
    public void testAddTwoTime() throws Exception {
        DateTime dateTimeFirst = new DateTime(2013, 10, 31, 11, 13, 0);
        DateTime modifiedTime = dateTimeFirst.plusHours(5);

        assertEquals(16, modifiedTime.getHourOfDay());
    }

    @Test
    public void testAddTwoTimeCausesDayChange() throws Exception {
        DateTime dateTimeFirst = new DateTime(2013, 10, 30, 11, 13, 0);
        DateTime modifiedTime = dateTimeFirst.plusHours(15);

        assertEquals(2, modifiedTime.getHourOfDay());
        assertEquals(31, modifiedTime.getDayOfMonth());
    }

    @Test
    public void testMethod() throws Exception {
        DateTime dateTime = new DateTime();
        DateTime modifiedTime = dateTime.withHourOfDay(10).withMinuteOfHour(30).withSecondOfMinute(15);

        assertEquals(10, modifiedTime.getHourOfDay());
        assertEquals(30, modifiedTime.getMinuteOfHour());
        assertEquals(15, modifiedTime.getSecondOfMinute());
    }

    @Test
    public void testMathRoundFirst() throws Exception {
        double divided = 5 / (1.8);
        int round = (int) Math.round(divided);

        assertEquals(3, round);
    }

    @Test
    public void testMathRoundSecond() throws Exception {
        double divided = 5 / (1.9);
        int round = (int) Math.round(divided);

        assertEquals(3, round);
    }

    @Test
    public void testMathRoundThird() throws Exception {
        double divided = 5 / (2);
        int round = (int) Math.round(divided);

        assertEquals(2, round);
    }

    @Test
    public void testMathRoundFourth() throws Exception {
        double divided = 5d / 3d;
        int round = (int) Math.round(divided);

        assertEquals(2, round);
    }

    @Test
    public void testMethoda() throws Exception {
        double summa = 192;
        int hours = (int) Math.floor(summa / 60);
        int minutes = 192 % 60;

        assertEquals(3, hours);
        assertEquals(12, minutes);
    }

    @Test
    public void testTest() throws Exception {
        int firstDay = firstDay(2013, Calendar.NOVEMBER);
        assertEquals(Calendar.FRIDAY, firstDay);
        int workDays = getWorkDays(2013, Calendar.NOVEMBER);
        assertEquals(21, workDays);
        workDays = getWorkDays(2013, Calendar.DECEMBER);
        assertEquals(22, workDays);
        workDays = getWorkDays(2013, Calendar.JANUARY);
        assertEquals(23, workDays);
        workDays = getWorkDays(2013, Calendar.FEBRUARY);
        assertEquals(20, workDays);
    }
    
    @Test
    public void listSizeTest() {
        List<Object> list = new ArrayList<Object>();
        
        Object a = new Object();
        Object b = new Object();
        
        list.add(a);
        list.add(b);
        
        assertEquals(b, list.get(list.size()-1));
    }
    
    private int firstDay(int year, int month) {
        Calendar calendar = new GregorianCalendar(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private int getWorkDays(int year, int month) {
        int numberOfWorkDays = 0;
        
        Calendar calendar = new GregorianCalendar(year, month, 1);
        int daysInMonth = daysInMonth(calendar);
        for (int i = 1; i <= daysInMonth; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            if (! isWeekend(calendar.get(Calendar.DAY_OF_WEEK))) {
                numberOfWorkDays++;
            }
        }
        
        return numberOfWorkDays;
    }
    
    private int daysInMonth(Calendar calendar) {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private boolean isWeekend(int day) {
        return day == Calendar.SATURDAY || day == Calendar.SUNDAY;
    }

}
