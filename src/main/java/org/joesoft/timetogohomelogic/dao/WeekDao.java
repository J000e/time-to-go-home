package org.joesoft.timetogohomelogic.dao;

import org.joesoft.timetogohomelogic.operator.WeekRecord;

public interface WeekDao {

    public WeekRecord getWeekRecordByWeekNumber(int weekNumber);
    
}
