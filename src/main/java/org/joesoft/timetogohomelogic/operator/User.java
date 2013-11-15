package org.joesoft.timetogohomelogic.operator;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.joesoft.timetogohomelogic.common.CalendarUtil;
import org.joesoft.timetogohomelogic.common.PropertyReader;

public class User {

    private static final CalendarUtil calendarUtil = new CalendarUtil();
    private final String name;
    private final TimeSelector timeSelector;
    private Set<DayRecord> dayRecords;
    

    public User(String name) {
        this.name = name;
        this.dayRecords = new HashSet<DayRecord>();
        this.timeSelector = new TimeSelector(dayRecords);
    }

    public void startNewWorkDay(Date actualDate, PropertyReader propertyReader) {
        WorkDayRecord workDayRecord = createWorkDayRecord(actualDate, propertyReader);
        dayRecords.add(workDayRecord);
    }

    public Set<DayRecord> getAllDays() {
        return Collections.unmodifiableSet(dayRecords);
    }
    
    public Set<DayRecord> getWeek(int weekNumber) {
        
        return null;
    }
    
    private WorkDayRecord createWorkDayRecord(Date actualDate, PropertyReader propertyReader) {
        WorkDayRecord workDayRecord = new WorkDayRecord(actualDate, propertyReader);
        workDayRecord.setArrival(HoursAndMinutes.fromDate(actualDate));

        return workDayRecord;
    }
/*
    
    int year = calendarUtil.getFromDate(Calendar.YEAR, actualDate);
        int monthIndex = calendarUtil.getFromDate(Calendar.MONTH, actualDate);
        MonthRecord monthRecord = getMonthRecord(year, monthIndex);
        WorkDayRecord workDayRecord = createWorkDayRecord(actualDate, propertyReader);
        monthRecord.addDayRecord(workDayRecord);
        Set<MonthRecord> months = getMonthSet(year);
        months.add(monthRecord);
    
    private MonthRecord getMonthRecord(int year, int monthIndex) {
        final Set<MonthRecord> getYear = workRecords.get(year);
        MonthRecord monthRecord;
        
        try {
            monthRecord = findMonthRecord(getYear, monthIndex);
        } catch (RecordNotFoundException e) {
            monthRecord = new MonthRecord(year, monthIndex);
        }
        
        return monthRecord;
    }

    private MonthRecord findMonthRecord(final Set<MonthRecord> getYear, int monthIndex) {
        MonthRecord monthRecord = null;
        if (getYear != null) {
            for (MonthRecord item : getYear) {
                if (item.getMonthIndex() == monthIndex) {
                    monthRecord = item;
                    break;
                }
            }
        }

        if (monthRecord == null) {
            throw new RecordNotFoundException();
        }

        return monthRecord;
    }

    private Set<MonthRecord> getMonthSet(int year) {
        final Set<MonthRecord> setFound = workRecords.get(year);
        if (setFound == null) {
            return new HashSet<MonthRecord>();
        } else {
            return setFound;
        }
    }

    private static class RecordNotFoundException extends RuntimeException {
        public RecordNotFoundException() {
        }
    }
    */
}
