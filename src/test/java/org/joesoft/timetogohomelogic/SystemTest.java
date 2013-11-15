package org.joesoft.timetogohomelogic;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import org.joda.time.DateTime;
import org.joesoft.timetogohomelogic.common.CalendarUtil;
import org.joesoft.timetogohomelogic.common.PropertyReader;
import org.joesoft.timetogohomelogic.common.TestWeekDao;
import org.joesoft.timetogohomelogic.common.WeekRecordProvider;
import org.joesoft.timetogohomelogic.dao.WeekDao;
import org.joesoft.timetogohomelogic.operator.DayRecord;
import org.joesoft.timetogohomelogic.operator.TimeSelector;
import org.joesoft.timetogohomelogic.operator.User;
import org.junit.Before;
import org.junit.Test;

public class SystemTest {
    
    private WeekRecordProvider weekRecordProvider;
    private TimeSelector timeSelector;
    
    @Before
    public void setUp() {
        WeekDao testWeekDao = new TestWeekDao();
        weekRecordProvider = new WeekRecordProvider(testWeekDao);
    }
    
    @Test
    public void newUserSetOneWorkday() {
        User operator = new User("john rambo");
        
        Date actualDate = new DateTime(2013, 11, 13, 13, 54).toDate();
        CalendarUtil calendarUtil = new CalendarUtil();
        int weekNumber = calendarUtil.getFromDate(Calendar.WEEK_OF_YEAR, actualDate);
        PropertyReader propertyReader = new TestPropertyReader();
        
        operator.startNewWorkDay(actualDate, propertyReader);
        timeSelector = new TimeSelector(operator.getAllDays());
        Set<DayRecord> recordsFound
                = timeSelector.getRecordsByWeekNumber(weekNumber);
        
        assertEquals(1, recordsFound.size());
    }
}
