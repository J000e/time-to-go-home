package org.joesoft.timetogohomelogic;

import java.util.ArrayList;
import java.util.List;

public class WeekRecord {
    private static final int LENGTH_OF_A_WEEK = 7;
    private final int weekNumber;
    private final List<WorkDayRecord> workDays;
    private final List<FreeDayRecord> freeDays;
    
    public WeekRecord(int weekNumber) {
        this.weekNumber = weekNumber;
        workDays = new ArrayList<WorkDayRecord>(LENGTH_OF_A_WEEK);
        freeDays = new ArrayList<FreeDayRecord>(LENGTH_OF_A_WEEK);
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public int getNumberOfRecords() {
        return workDays.size() + freeDays.size();
    }
    
    public void addWorkDayRecord(WorkDayRecord record) {
        validateInput(record);
        workDays.add(record);
    }
    
    public void addFreeDayRecord(FreeDayRecord record) {
        validateInput(record);
        freeDays.add(record);
    }

    public HoursAndMinutes getTotalWorkTime() {
        HoursAndMinutes workHours = new HoursAndMinutes(0, 0);
        for (WorkDayRecord workDayRecord : workDays) {
            workHours = workDayRecord.getTotalWorkTime().plus(workHours);
        }
        return workHours;
    }

    public HoursAndMinutes getAvarageWorkingHours() {
        return getTotalWorkTime().divide(workDays.size());
    }

    public HoursAndMinutes getWorkhoursLeft() {
        return null;
    }

    private void validateInput(Object record) throws IllegalArgumentException {
        if (record == null) {
            throw new IllegalArgumentException("Day record should not be null.");
        }
        
        if (workDays.size() + freeDays.size() >= LENGTH_OF_A_WEEK) {
            throw new MaximumDaysPerWeekExceededException();
        }
    }

}
