package org.joesoft.timetogohomelogic;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User {

    private static final CalendarUtil calendarUtil = new CalendarUtil();
    private final String name;
    private Map<Integer, Set<MonthRecord>> workRecords;

    public User(String name) {
        this.name = name;
        this.workRecords = new HashMap<Integer, Set<MonthRecord>>();
    }

    public void startNewWorkDay(Date actualDate, PropertyReader propertyReader) {
        int year = calendarUtil.getFromDate(Calendar.YEAR, actualDate);
        int monthIndex = calendarUtil.getFromDate(Calendar.MONTH, actualDate);
        MonthRecord monthRecord = getMonthRecord(year, monthIndex);
        WorkDayRecord workDayRecord = createWorkDayRecord(actualDate, propertyReader);
        monthRecord.addDayRecord(workDayRecord);
        Set<MonthRecord> months = getMonthSet(year);
        months.add(monthRecord);
        workRecords.put(year, months);
    }

    public Set<DayRecord> getAllDays() {
        Set<DayRecord> allDay = new HashSet<DayRecord>();
        for (Set<MonthRecord> month : workRecords.values()) {
            for (MonthRecord monthRecord : month) {
                allDay.addAll(monthRecord.getDays());
            }
        }

        return allDay;
    }

    private WorkDayRecord createWorkDayRecord(Date actualDate, PropertyReader propertyReader) {
        WorkDayRecord workDayRecord = new WorkDayRecord(actualDate, null, propertyReader);
        workDayRecord.setArrival(HoursAndMinutes.fromDate(actualDate).hours,
                HoursAndMinutes.fromDate(actualDate).minutes);

        return workDayRecord;
    }

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
}
