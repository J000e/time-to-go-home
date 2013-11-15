package org.joesoft.timetogohomelogic.common;

import java.util.HashMap;
import java.util.Map;
import org.joesoft.timetogohomelogic.dao.WeekDao;
import org.joesoft.timetogohomelogic.operator.WeekRecord;

public class TestWeekDao implements WeekDao {
    private Map<Integer, WeekRecord> weekRecords;
    

    public TestWeekDao() {
        weekRecords = new HashMap<Integer, WeekRecord>();
    }
    
    public void addRecord(WeekRecord record) {
        weekRecords.put(record.getWeekNumber(), record);
    }

    public WeekRecord getWeekRecordByWeekNumber(int weekNumber) {
        WeekRecord result = weekRecords.get(weekNumber);
        if (result == null) {
            throw new RecordNotFoundException(weekNumber);
        }
        
        return result;
    }
    
    public int countRecords() {
        return weekRecords.size();
    }
    
}
