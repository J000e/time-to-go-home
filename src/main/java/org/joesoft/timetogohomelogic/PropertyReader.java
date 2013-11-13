package org.joesoft.timetogohomelogic;

public interface PropertyReader {
    String getProperty(PropertyReaderImpl.PropertyName key);
    
    enum PropertyName {
        MINIMUM_LUNCH_TIME("lunch.minimum-time-in-minutes"),
        WORKING_HOURS_PER_DAY("working-hours.per-day");
        
        private final String path;

        private PropertyName(String path) {
            this.path = path;
        }
        
        protected String getPath() {
            return path;
        }
    }
}
