package org.joesoft.timetogohomelogic;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class PropertyReaderTest {
    private PropertyReaderImpl reader;
    
    @Before
    public void setUp() throws IOException {
        reader = new PropertyReaderImpl();
    }
    
    @Test
    public void testMinimumLunchTime() throws Exception {
        assertEquals("30", reader.getProperty(
                PropertyReaderImpl.PropertyName.MINIMUM_LUNCH_TIME));
        
    }
    
    @Test
    public void testWorkingHoursPerDay() throws Exception {
        assertEquals("8", reader.getProperty(
                        PropertyReaderImpl.PropertyName.WORKING_HOURS_PER_DAY));
    }
}
