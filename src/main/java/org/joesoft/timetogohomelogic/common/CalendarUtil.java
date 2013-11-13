package org.joesoft.timetogohomelogic.common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarUtil {
    private final Calendar calendar;

    public CalendarUtil() {
        this.calendar = GregorianCalendar.getInstance();
    }

    public int getFromDate(int calendarField, Date actualDate) {
        calendar.setTime(actualDate);

        return calendar.get(calendarField);
    }

    public int getNumberOfWorkDays(int year, int month) {
        int numberOfWorkDays = 0;
        calendar.set(year, month, 1);

        int daysInMonth = daysInMonth(calendar);
        for (int i = 1; i <= daysInMonth; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            if (!isWeekend(calendar.get(Calendar.DAY_OF_WEEK))) {
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
