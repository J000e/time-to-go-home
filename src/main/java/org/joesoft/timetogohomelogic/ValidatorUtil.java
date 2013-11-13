package org.joesoft.timetogohomelogic;

public class ValidatorUtil {
    
    public boolean isAnyOfThemNull(Object... verifyables) {
        if (verifyables == null) {
            return true;
        }
        
        for (Object verifyable : verifyables) {
            if (verifyable == null) {
                return true;
            }
        }
        return false;
    }
    
}
