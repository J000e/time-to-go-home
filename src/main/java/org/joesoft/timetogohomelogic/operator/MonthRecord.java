package org.joesoft.timetogohomelogic.operator;

import java.util.HashSet;
import java.util.Set;
import org.joesoft.timetogohomelogic.common.CalendarUtil;
import org.joesoft.timetogohomelogic.common.ValidatorUtil;

public class MonthRecord {
    private static final ValidatorUtil validator = new ValidatorUtil();
    private static final CalendarUtil calendarUtil = new CalendarUtil();
    private final Set<DayRecord> days;
    private final int monthIndex;
    private long numberOfWorkDays;
    

    public MonthRecord(int year, int monthIndex) {
        numberOfWorkDays = calendarUtil.getNumberOfWorkDays(year, monthIndex);
        this.monthIndex = monthIndex;
        days = new HashSet<DayRecord>();
    }
    
    public void addDayRecord(DayRecord dayRecord) {
        if (!validator.isNull(dayRecord)) {
            days.add(dayRecord);
        }
    }

    public Set<DayRecord> getDays() {
        return days;
    }

    public int getMonthIndex() {
        return monthIndex;
    }

    public enum Month {
        JANUARY(1), FEBRUARY(2), MARCH(3),
        APRIL(4), MAY(5), JUNE(6),
        JULY(7), AUGUST(8), SEPTEMBER(9),
        OCTOBER(10), NOVEMBER(11), DECEMBER(12);

        public static Month getByIndex(int monthIndex) {
            for (Month m : Month.values()) {
                if (m.index == monthIndex) {
                    return m;
                }
            }
            throw new IllegalArgumentException("Invalid month index " + monthIndex);
        }

        private int index;

        private Month(int index) {
            this.index = index;
        }

        public int getMonthIndex() {
            return index;
        }
    }

}
