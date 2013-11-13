package org.joesoft.timetogohomelogic;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkDayRecord extends DayRecord {

    public static final HoursAndMinutes ZERO_WORKHOURS = new HoursAndMinutes(0, 0);
    private static final ValidatorUtil validator = new ValidatorUtil();
    private HoursAndMinutes arrival;
    private HoursAndMinutes lunchStarts;
    private HoursAndMinutes lunchEnds;
    private HoursAndMinutes leave;
    private final HoursAndMinutes minimumLunchTime;
    private final HoursAndMinutes hoursToWork;

    public WorkDayRecord(Date date, WeekRecord weekRecord, PropertyReader propertyReader) {
        super(date);
        final int minutes = getProperty(propertyReader, PropertyReader.PropertyName.MINIMUM_LUNCH_TIME);
        final int hours = getProperty(propertyReader, PropertyReader.PropertyName.WORKING_HOURS_PER_DAY);
        minimumLunchTime = new HoursAndMinutes(0, minutes);
        hoursToWork = new HoursAndMinutes(hours, 0);
    }
    
    public HoursAndMinutes countWorktimeForWeekly() {
        return null;
    }
    
    public void setArrival(int hours, int minutes) {
        arrival = setUpTime(hours, minutes);
    }

    public void setLunchStart(int hours, int minutes) {
        HoursAndMinutes time = setUpTime(hours, minutes);
        mustGreaterThan(time, arrival);
        lunchStarts = time;
    }

    public void setLunchEnds(int hours, int minutes) {
        HoursAndMinutes time = setUpTime(hours, minutes);
        mustGreaterThan(time, lunchStarts);
        time = verifyMinimumLuchTime(time);

        lunchEnds = time;
    }

    public void setLeave(int hours, int minutes) {
        HoursAndMinutes time = setUpTime(hours, minutes);
        mustGreaterThan(time, lunchEnds);
        leave = time;
    }

    public HoursAndMinutes getTotalWorkTime() {
        if (validator.isAnyOfThemNull(arrival, lunchStarts, lunchEnds, leave)) {
            return ZERO_WORKHOURS;
        }
        return lunchStarts.minus(arrival).plus(leave.minus(lunchEnds));
    }

    public HoursAndMinutes getIdealLeaveTime() {
        if (validator.isAnyOfThemNull(arrival)) {
            throw new IllegalStateException("You have to set arrival time first.");
        }

        return calculateIdealLeaveTime(hoursToWork);
    }
    
    public HoursAndMinutes calculateTimeWorked(HoursAndMinutes actualTime) {
        HoursAndMinutes timeWorked;
        if (validator.isAnyOfThemNull(arrival)|| eventIsBefore(actualTime, arrival)) {
            timeWorked = ZERO_WORKHOURS;
        } else if (validator.isAnyOfThemNull(lunchStarts) || eventIsBefore(actualTime, lunchStarts)) {
            timeWorked = actualTime.minus(arrival);
        } else if (validator.isAnyOfThemNull(lunchEnds) || eventIsBefore(actualTime, lunchEnds)){
            timeWorked = lunchStarts.minus(arrival);
        } else if (validator.isAnyOfThemNull(leave) || eventIsBefore(actualTime, leave)){
            timeWorked = lunchStarts.minus(arrival).plus(actualTime.minus(lunchEnds));
        } else {
            timeWorked = getTotalWorkTime();
        }

        return timeWorked;
    }

    public HoursAndMinutes getLunchEnds() {
        return lunchEnds;
    }
    
    public HoursAndMinutes getHoursToWork() {
        return hoursToWork;
    }
    
    private boolean eventIsBefore(HoursAndMinutes actualTime, HoursAndMinutes event) {
        return !validator.isAnyOfThemNull(event) && event.isGreaterThan(actualTime);
    }

    private HoursAndMinutes calculateIdealLeaveTime(HoursAndMinutes targetTime) {
        HoursAndMinutes idealLeaveTime;
        if (validator.isAnyOfThemNull(lunchStarts)) {
            idealLeaveTime = arrival.plus(targetTime).plus(minimumLunchTime);
        } else if (validator.isAnyOfThemNull(lunchEnds)) {
            HoursAndMinutes lunchMinusArrival = lunchStarts.minus(arrival);
            idealLeaveTime = targetTime.minus(lunchMinusArrival).plus(lunchStarts).plus(minimumLunchTime);
        } else {
            HoursAndMinutes lunchMinusArrival = lunchStarts.minus(arrival);
            idealLeaveTime = targetTime.minus(lunchMinusArrival).plus(lunchEnds);
        }

        return idealLeaveTime;
    }
    
    private HoursAndMinutes verifyMinimumLuchTime(HoursAndMinutes time) {
        HoursAndMinutes minimumTime = lunchStarts.plus(minimumLunchTime);
        if (minimumTime.isGreaterThan(time)) {
            time = minimumTime;
        }
        return time;
    }

    private HoursAndMinutes setUpTime(int hours, int minutes) {
        return new HoursAndMinutes(hours, minutes);
    }

    private void mustGreaterThan(HoursAndMinutes newTime, HoursAndMinutes targetTime) {
        if (targetTime == null) {
            throw new IllegalStateException("You have to set the time of the previous event first.");
        }

        if (newTime.compareTo(targetTime) < 0) {
            throw new IllegalArgumentException("Later events should have later time.");
        }
    }

    private int getProperty(PropertyReader propertyReader, PropertyReader.PropertyName propertyName) {
        int propertyValue = 0;
        try {
            propertyValue = Integer.valueOf(propertyReader.getProperty(propertyName));
        } catch (NumberFormatException e) {
            Logger.getLogger(this.getClass().getName()).log(
                    Level.SEVERE, "Mailformed property file: property should be a number", e);
        }

        return propertyValue;
    }
}
