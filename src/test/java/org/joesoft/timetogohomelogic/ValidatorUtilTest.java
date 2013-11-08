package org.joesoft.timetogohomelogic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ValidatorUtilTest {
    ValidatorUtil util;

    @Before
    public void setUp() {
        util = new ValidatorUtil();
    }
    
    @Test
    public void testIsAnyOfThemNullWhenParameterIsNull() throws Exception {
        assertTrue(util.isAnyOfThemNull(null));
    }
    
    @Test
    public void testIsAnyOfThemNullWhenParameterObjectIsNull() throws Exception {
        Object o = null;
        assertTrue(util.isAnyOfThemNull(o));
    }
    
    @Test
    public void testOneParameterObjectIsNull() throws Exception {
        Object notNull = new Object();
        Object nullObject = null;
        
        assertTrue(util.isAnyOfThemNull(notNull, nullObject));
    }
    
    @Test
    public void testTheParameterIsNotNull() throws Exception {
        Object notNull = new Object();
        
        assertFalse(util.isAnyOfThemNull(notNull));
    }
    
    @Test
    public void testNoneOfTheParameterIsNull() throws Exception {
        Object notNull = new Object();
        Object OtherNotNull = new Object();
        
        assertFalse(util.isAnyOfThemNull(notNull, OtherNotNull));
    }
}
