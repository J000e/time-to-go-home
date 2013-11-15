package org.joesoft.timetogohomelogic.operator;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class TimeSelector {
    private final Set<DayRecord> dayRecords;

    public TimeSelector(Set<DayRecord> dayRecords) {
        this.dayRecords = dayRecords;
    }
    
    public Set<DayRecord> getRecordsByWeekNumber(int weekNumber) {
        Set<DayRecord> records = new HashSet<DayRecord>();
        Calendar calendar = Calendar.getInstance();
        for (DayRecord dayRecord : dayRecords) {
            calendar.set(Calendar.YEAR, dayRecord.year);
            calendar.set(Calendar.MONTH, dayRecord.monthNumber);
            calendar.set(Calendar.DAY_OF_MONTH, dayRecord.dayNumber);
            if (calendar.get(Calendar.WEEK_OF_YEAR) == weekNumber) {
                records.add(dayRecord);
            }
        }
        
        return records;
    }
    
}
