package org.joesoft.timetogohomelogic;

import org.joesoft.timetogohomelogic.common.PropertyReader;
import org.joesoft.timetogohomelogic.common.PropertyReader;

public class TestPropertyReader implements PropertyReader{
    public static final String MINIMUM_LUNCH_TIME = "30";
    public static final String WORKING_HOURS_PER_DAY = "8";

    public String getProperty(PropertyName key) {
        if (key.equals(PropertyName.MINIMUM_LUNCH_TIME)) {
            return MINIMUM_LUNCH_TIME;
        }
        
        return WORKING_HOURS_PER_DAY;
    }
    
}
