package org.joesoft.timetogohomelogic.common;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(int weekNumber) {
        super(String.format("Record with id: %d not found.", weekNumber));
    }
    
}
