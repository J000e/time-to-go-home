package org.joesoft.timetogohomelogic.common;

public class ValidatorUtil {
    
    public boolean isNull(Object verifyables) {
        return isAnyOfThemNull(verifyables);
    }
    
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
