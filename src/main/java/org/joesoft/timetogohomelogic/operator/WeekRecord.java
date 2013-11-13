package org.joesoft.timetogohomelogic.operator;

import org.joesoft.timetogohomelogic.common.PropertyReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeekRecord {

    private static final int LENGTH_OF_A_WEEK = 7;
    private final int workingHoursPerDay;
    private final int weekNumber;
    private final List<WorkDayRecord> workDays;
    private final List<FreeDayRecord> freeDays;

    public WeekRecord(int weekNumber, PropertyReader propertyReader) {
        this.weekNumber = weekNumber;
        workingHoursPerDay = getWorkingHoursPerDay(propertyReader);
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

    public HoursAndMinutes getTotalWorkTime(Date actualDate) {
        HoursAndMinutes workHours = new HoursAndMinutes(0, 0);
        for (WorkDayRecord workDayRecord : workDays) {
            if (workDayRecord.isToday(actualDate)) {
                HoursAndMinutes actualTime = HoursAndMinutes.fromDate(actualDate);
                workHours = workDayRecord.calculateTimeWorked(actualTime).plus(workHours);
            } else {
                workHours = workDayRecord.getTotalWorkTime().plus(workHours);
            }
        }
        return workHours;
    }

    public HoursAndMinutes getAvarageWorkingHours(Date actualTime) {
        return getTotalWorkTime(actualTime).divide(workDays.size());
    }
    
    private int getWorkingHoursPerDay(PropertyReader propertyReader) throws NumberFormatException {
        return Integer.parseInt(propertyReader.getProperty(PropertyReader.PropertyName.WORKING_HOURS_PER_DAY));
    }

    private void validateInput(Object record) throws IllegalArgumentException {
        if (record == null) {
            throw new IllegalArgumentException("Day record should not be null.");
        }

        if (workDays.size() + freeDays.size() >= LENGTH_OF_A_WEEK) {
            throw new MaximumDaysPerWeekExceededException();
        }
    }

    public HoursAndMinutes getHoursLeft(int workDaysInTheWeek, int daysRemaining, Date actualTime) {
        HoursAndMinutes totalWorkTimeToDo = getTotalWorkTimeToDo(workDaysInTheWeek);

        return totalWorkTimeToDo.minus(getTotalWorkTime(actualTime)).divide(daysRemaining);
    }

    private HoursAndMinutes getTotalWorkTimeToDo(int summaDays) {
        HoursAndMinutes totalWorkTimeToDo = new HoursAndMinutes();
        for (WorkDayRecord workDayRecord : workDays) {
            totalWorkTimeToDo = workDayRecord.getHoursToWork().plus(totalWorkTimeToDo);
        }
        int daysLeft = summaDays - workDays.size();
        HoursAndMinutes lastActiveDaysWorkhours = getHoursToWork();
        if (daysLeft > 0) {
            totalWorkTimeToDo = totalWorkTimeToDo.plus(lastActiveDaysWorkhours.multiply(daysLeft));
        }

        return totalWorkTimeToDo;
    }

    private HoursAndMinutes getHoursToWork() {
        HoursAndMinutes result;
        if (workDays.size() > 0) {
            result = workDays.get(workDays.size() - 1).getHoursToWork();
        } else {
            result = new HoursAndMinutes(workingHoursPerDay, 0);
        
        }
        return result;
    }

}
