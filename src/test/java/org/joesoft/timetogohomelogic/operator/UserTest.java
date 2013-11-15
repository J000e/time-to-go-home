package org.joesoft.timetogohomelogic.operator;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.Set;
import org.joda.time.DateTime;
import org.joesoft.timetogohomelogic.TestPropertyReader;
import org.joesoft.timetogohomelogic.common.PropertyReader;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private static final String DUMMY_USERNAME = "john rambo";
    private final PropertyReader propertyReader = new TestPropertyReader();
    private User user;

    @Before
    public void setUp() {
        user = new User(DUMMY_USERNAME);
    }

    @Test
    public void testNewUserHasZeroRecords() throws Exception {
        Set<DayRecord> allDays = user.getAllDays();

        assertEquals(0, allDays.size());
    }

    @Test
    public void testStartADay() throws Exception {
        Date dateOfTheDay = new DateTime(2013, 11, 07, 8, 11).toDate();
        user.startNewWorkDay(dateOfTheDay, propertyReader);

        Set<DayRecord> records = user.getAllDays();
        assertEquals(1, records.size());
    }
    
    @Test
    public void testStartADayAndANextOne() throws Exception {
        Date dateOfTheDay = new DateTime(2013, 11, 07, 8, 11).toDate();
        user.startNewWorkDay(dateOfTheDay, propertyReader);
        Date dateOfTheSecondDay = new DateTime(2013, 11, 8, 8, 11).toDate();
        user.startNewWorkDay(dateOfTheSecondDay, propertyReader);

        Set<DayRecord> records = user.getAllDays();
        assertEquals(2, records.size());
    }

}
