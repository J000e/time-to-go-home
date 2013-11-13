package org.joesoft.timetogohomelogic.operator;

import org.joesoft.timetogohomelogic.common.CalendarUtil;
import java.util.Calendar;
import java.util.Date;

public class DayRecord {
    private final CalendarUtil calendarUtil;
    protected int year;
    protected int monthNumber;
    protected int dayNumber;

    protected DayRecord(Date date) {
        calendarUtil = new CalendarUtil();
        this.year = getYear(date);
        this.monthNumber = getMonth(date);
        this.dayNumber = getDay(date);
    }

    protected boolean isToday(Date actualDate) {
        return this.year == getYear(actualDate) &&
                this.monthNumber == getMonth(actualDate) &&
                this.dayNumber == getDay(actualDate);
    }

    private int getDay(Date date) {
        return calendarUtil.getFromDate(Calendar.DAY_OF_MONTH, date);
    }

    private int getMonth(Date date) {
        return calendarUtil.getFromDate(Calendar.MONTH, date);
    }
    
    private int getYear(Date date) {
        return calendarUtil.getFromDate(Calendar.YEAR, date);
    }
    
}
