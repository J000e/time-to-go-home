package org.joesoft.timetogohomelogic.common;

import java.util.Calendar;
import java.util.Date;
import org.joesoft.timetogohomelogic.dao.WeekDao;
import org.joesoft.timetogohomelogic.operator.WeekRecord;

public class WeekRecordProvider {
    private static final CalendarUtil calendarUtil = new CalendarUtil();
    private final WeekDao weekDao;

    public WeekRecordProvider(WeekDao weekDao) {
        this.weekDao = weekDao;
    }
    
    public WeekRecord getByDate(Date actualDate, PropertyReader propertyReader) {
        int weekNumber = calendarUtil.getFromDate(Calendar.WEEK_OF_YEAR, actualDate);
        
        return getWeekRecordByWeekNumber(weekNumber, propertyReader);
    }

    private WeekRecord getWeekRecordByWeekNumber(int weekNumber, PropertyReader propertyReader) {
        WeekRecord result;
        try {
            result = weekDao.getWeekRecordByWeekNumber(weekNumber);
        } catch (RecordNotFoundException e) {
            result = new WeekRecord(weekNumber, propertyReader);
        }
        
        return result;
    }
    
}
