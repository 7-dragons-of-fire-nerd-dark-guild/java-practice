package com.sevendragons.hashcode2016.deliverysystem;

import org.junit.Test;

import java.util.*;

import static com.sevendragons.hashcode2016.deliverysystem.DeliverySystem.generateOutputLines;
import static org.junit.Assert.*;

public class DeliverySystemTest {
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
        Pack pack = new Pack(0);
        orderMap.put(0, new Order(0, 1, 1, pack));
        orderMap.put(1, new Order(1, 3, 4, pack));

        int[][] matrix = DeliverySystem.createMatrix(orderMap, orderMap);
        int[][] expected = {{0, 4}, {4, 0}};
        assertArrayEquals(expected[0], matrix[0]);
        assertArrayEquals(expected[1], matrix[1]);
    }

    @Test
    public void test_createMatrix_3x3() {
        Map<Integer, MapItem> orderMap = new HashMap<>();
        Pack pack = new Pack(0);
        orderMap.put(0, new Order(0, 1, 1, pack));
        orderMap.put(1, new Order(1, 3, 4, pack));
        orderMap.put(2, new Order(2, 4, 5, pack));

        int[][] matrix = DeliverySystem.createMatrix(orderMap, orderMap);
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
        Pack pack = new Pack(0);
        orderMap.put(0, new Order(0, 1, 1, pack));
        orderMap.put(1, new Order(1, 3, 4, pack));

        Map<Integer, MapItem> warehouseMap = new HashMap<>();
        warehouseMap.put(0, new Warehouse(0, 2, 2, pack));
        warehouseMap.put(1, new Warehouse(1, 4, 5, pack));
        warehouseMap.put(2, new Warehouse(2, 6, 7, pack));

        int[][] matrix = DeliverySystem.createMatrix(orderMap, warehouseMap);
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
        Pack pack = new Pack(0);

        // root(2*2 + 3*3) -> root(13)
        assertEquals(4, DeliverySystem.calculateDistance(new Order(0, 1, 1, pack), new Order(0, 3, 4, pack)));
        // root(3*3 + 3*3) -> root(18)
        assertEquals(5, DeliverySystem.calculateDistance(new Order(0, 0, 1, pack), new Order(0, 3, 4, pack)));
        // root(3*3 + 4*4) -> root(25)
        assertEquals(5, DeliverySystem.calculateDistance(new Order(0, 0, 1, pack), new Order(0, 3, 5, pack)));
    }

    @Test
    public void test_generateOutputLines_example_1() {
        Drone drone0 = new Drone(0, -1, -1, -1, null);
        Drone drone1 = new Drone(1, -1, -1, -1, null);
        Warehouse warehouse0 = new Warehouse(0, -1, -1, null);
        Warehouse warehouse1 = new Warehouse(1, -1, -1, null);
        Order order0 = new Order(0, -1, -1, null);
        Order order1 = new Order(1, -1, -1, null);
        Order order2 = new Order(2, -1, -1, null);

        List<Command> commands = Arrays.asList(
                new LoadCommand(drone0, warehouse0, Pack.fromCounts(1, 1)),
                new DeliverCommand(drone0, order0, Pack.fromCounts(1)),
                new LoadCommand(drone0, warehouse1, Pack.fromCounts(0, 0, 1)),
                new DeliverCommand(drone0, order0, Pack.fromCounts(0, 0, 1)),

                new LoadCommand(drone1, warehouse1, Pack.fromCounts(0, 0, 1)),
                new DeliverCommand(drone1, order2, Pack.fromCounts(0, 0, 1)),
                new LoadCommand(drone1, warehouse0, Pack.fromCounts(1)),
                new DeliverCommand(drone1, order1, Pack.fromCounts(1))
                );

        List<String> lines = generateOutputLines(commands);
        assertEquals(Arrays.asList(
                "9\n",
                "0 L 0 0 1\n",
                "0 L 0 1 1\n",
                "0 D 0 0 1\n",
                "0 L 1 2 1\n",
                "0 D 0 2 1\n",
                "1 L 1 2 1\n",
                "1 D 2 2 1\n",
                "1 L 0 0 1\n",
                "1 D 1 0 1\n"
        ), lines);
    }
}
