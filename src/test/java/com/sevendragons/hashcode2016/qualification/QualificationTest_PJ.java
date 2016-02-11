package com.sevendragons.hashcode2016.qualification;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class QualificationTest_PJ {

    @Test
    public void test_something_gives_something() {
        // just to have some static imports ready to use
        // delete when you have actual meaningful tests
        assertEquals(Arrays.asList(1, 2), new ArrayList<>(Arrays.asList(1, 2)));
        assertNotEquals(Collections.emptyList(), Collections.emptySet());
    }
}
