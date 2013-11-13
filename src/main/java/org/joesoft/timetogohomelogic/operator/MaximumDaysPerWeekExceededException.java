package org.joesoft.timetogohomelogic.operator;

public class MaximumDaysPerWeekExceededException extends RuntimeException{

    public MaximumDaysPerWeekExceededException() {
        super("Tried to put more than seven days into a weekrecord.");
    }
    
}
