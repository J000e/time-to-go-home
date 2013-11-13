package org.joesoft.timetogohomelogic;

public class MaximumDaysPerWeekExceededException extends RuntimeException{

    public MaximumDaysPerWeekExceededException() {
        super("Tried to put more than seven days into a weekrecord.");
    }
    
}
