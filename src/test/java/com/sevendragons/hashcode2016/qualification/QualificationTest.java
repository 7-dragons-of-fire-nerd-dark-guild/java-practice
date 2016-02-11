package com.sevendragons.hashcode2016.qualification;

import com.sevendragons.hashcode2016.qualification.Qualification.MapItem;
import com.sevendragons.hashcode2016.qualification.Qualification.Order;
import com.sevendragons.hashcode2016.qualification.Qualification.Warehouse;
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
    public void test_example_1() {
        String text = "" +
                "100 100 3 50 500\n" +
                "3\n" +
                "100 5 450\n" +
                "2\n" +
                "0 0\n" +
                "5 1 0\n" +
                "5 5\n" +
                "0 10 2\n" +
                "3\n" +
                "1 1\n" +
                "2\n" +
                "2 0\n" +
                "3 3\n" +
                "3\n" +
                "0 0 0\n" +
                "5 6\n" +
                "1\n" +
                "2\n";
        Qualification.Input input = Qualification.Input.fromScanner(new Scanner(text));
        assertEquals(2, input.warehouseMap.size());
        assertEquals(5, input.warehouseMap.get(1).row);
        assertEquals(5, input.warehouseMap.get(1).col);

        assertEquals(3, input.orderMap.size());
        assertEquals(5, input.orderMap.get(2).row);
        assertEquals(6, input.orderMap.get(2).col);
        // NPE
//        assertEquals(1, input.orderMap.get(2).products.products.size());
    }

    @Test
    public void test_createMatrix_2x2() {
        Map<Integer, MapItem> orderMap = new HashMap<>();
        orderMap.put(0, new Order(0, 1, 1));
        orderMap.put(1, new Order(1, 3, 4));

        int[][] matrix = Qualification.createMatrix(orderMap, orderMap);
        int[][] expected = {{0, 4}, {4, 0}};
        assertArrayEquals(expected[0], matrix[0]);
        assertArrayEquals(expected[1], matrix[1]);
    }

    @Test
    public void test_createMatrix_3x3() {
        Map<Integer, MapItem> orderMap = new HashMap<>();
        orderMap.put(0, new Order(0, 1, 1));
        orderMap.put(1, new Order(1, 3, 4));
        orderMap.put(2, new Order(2, 4, 5));

        int[][] matrix = Qualification.createMatrix(orderMap, orderMap);
        int[][] expected = {
                {0, 4, 5},
                {4, 0, 2},
                {5, 2, 0}
        };
        assertArrayEquals(expected[0], matrix[0]);
        assertArrayEquals(expected[1], matrix[1]);
        assertArrayEquals(expected[2], matrix[2]);
    }

    @Test
    public void test_createMatrix_2x3() {
        Map<Integer, MapItem> orderMap = new HashMap<>();
        orderMap.put(0, new Order(0, 1, 1));
        orderMap.put(1, new Order(1, 3, 4));

        Map<Integer, MapItem> warehouseMap = new HashMap<>();
        warehouseMap.put(0, new Warehouse(0, 2, 2, new Qualification.Products(Collections.emptyMap())));
        warehouseMap.put(1, new Warehouse(1, 4, 5, new Qualification.Products(Collections.emptyMap())));
        warehouseMap.put(2, new Warehouse(2, 6, 7, new Qualification.Products(Collections.emptyMap())));

        int[][] matrix = Qualification.createMatrix(orderMap, warehouseMap);
        // note: not really tested....
        int[][] expected = {
                {2, 5, 8},
                {3, 2, 5}
        };
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
