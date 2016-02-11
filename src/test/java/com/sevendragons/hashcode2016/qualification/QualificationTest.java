package com.sevendragons.hashcode2016.qualification;

import com.sevendragons.hashcode2016.qualification.Qualification.MapItem;
import com.sevendragons.hashcode2016.qualification.Qualification.Order;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class QualificationTest {
    // Tips:
    //
    // Run a single test method in IntelliJ:
    //  => move the cursor inside the method body and press Control Shift F10
    //
    // Run all test methods of a class in IntelliJ:
    //  => move the cursor inside the class body but outside any method and press Control Shift F10
    //
    // Re-run the last test, whether one method or all methods:
    //  => press Shift F10
    //
    // Debug instead of run:
    //  => Control Shift F9 for first run
    //  => Shift F9 for re-run
    //
    // Run all test methods of a class with Maven:
    //  => mvn clean test -Dtest=FacadeTest
    //

    @Test
    public void test_something_gives_something() {
        // just to have some static imports ready to use
        // delete when you have actual meaningful tests
        assertEquals(Arrays.asList(1, 2), new ArrayList<>(Arrays.asList(1, 2)));
        assertNotEquals(Collections.emptyList(), Collections.emptySet());
    }

    @Test
    public void test_createMatrix_2x2() {
        Map<Integer, MapItem> orderMap = new HashMap<>();
        orderMap.put(0, new Order(0, 1, 1));
        orderMap.put(1, new Order(1, 3, 4));

        int[][] matrix = Qualification.createMatrix(orderMap);
        int[][] expected = {{0, 4}, {4, 0}};
        assertArrayEquals(expected[0], matrix[0]);
        assertArrayEquals(expected[1], matrix[1]);
    }

    @Test
    public void test_calculateDistance() {
        // root(2*2 + 3*3) -> root(13)
        assertEquals(4, Qualification.calculateDistance(new Order(0, 1, 1), new Order(0, 3, 4)));
        // root(3*3 + 3*3) -> root(18)
        assertEquals(5, Qualification.calculateDistance(new Order(0, 0, 1), new Order(0, 3, 4)));
        // root(3*3 + 4*4) -> root(25)
        assertEquals(5, Qualification.calculateDistance(new Order(0, 0, 1), new Order(0, 3, 5)));
    }
}
