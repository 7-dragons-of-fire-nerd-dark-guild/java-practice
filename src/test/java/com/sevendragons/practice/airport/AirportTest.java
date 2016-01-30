package com.sevendragons.practice.airport;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;

import static com.sevendragons.practice.airport.Airport.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AirportTest {
    private final int[][] exampleTransitMatrix = {
            {0, 100, 6},
            {60, 0, 8},
            {4, 2, 0}
    };

    private static final int[][] exampleDistanceMatrix = {
            {0, 10, 12},
            {10, 0, 5},
            {12, 5, 0}
    };

    @Test
    public void test_toTransitEdges() {
        int[][] matrix = exampleTransitMatrix;
        List<TransitEdge> expected = Arrays.asList(
                new TransitEdge(0, 1, 100),
                new TransitEdge(0, 2, 6),
                new TransitEdge(1, 0, 60),
                new TransitEdge(1, 2, 8),
                new TransitEdge(2, 0, 4),
                new TransitEdge(2, 1, 2)
        );
        assertEquals(expected, toTransitEdges(matrix));
    }

    @Test
    public void test_toDistanceEdges() {
        int[][] matrix = exampleDistanceMatrix;
        List<DistanceEdge> expected = Arrays.asList(
                new DistanceEdge(0, 1, 10),
                new DistanceEdge(0, 2, 12),
                new DistanceEdge(1, 0, 10),
                new DistanceEdge(1, 2, 5),
                new DistanceEdge(2, 0, 12),
                new DistanceEdge(2, 1, 5)
        );
        assertEquals(expected, toDistanceEdges(matrix));
    }

    @Test
    public void test_sorted_transit_by_passengers_descending() {
        TransitEdge transitEdge1 = new TransitEdge(1, 2, 1);
        TransitEdge transitEdge2 = new TransitEdge(3, 4, 2);
        TransitEdge transitEdge5 = new TransitEdge(5, 6, 5);

        List<TransitEdge> transitEdges = Arrays.asList(transitEdge2, transitEdge1, transitEdge5);
        sortByPassengersDescending(transitEdges);

        assertEquals(Arrays.asList(transitEdge5, transitEdge2, transitEdge1), transitEdges);
    }

    @Test
    public void test_sorted_by_distance_ascending() {
        DistanceEdge distanceEdge1 = new DistanceEdge(1, 2, 1);
        DistanceEdge distanceEdge2 = new DistanceEdge(3, 4, 2);
        DistanceEdge distanceEdge5 = new DistanceEdge(5, 6, 5);

        List<Airport.DistanceEdge> distanceEdges = Arrays.asList(distanceEdge2, distanceEdge1, distanceEdge5);
        sortByDistanceAscending(distanceEdges);

        assertEquals(Arrays.asList(distanceEdge1, distanceEdge2, distanceEdge5), distanceEdges);
    }

    @Test
    public void test_allocate_to_closest() {
        Map<Integer, Integer> allocations = new HashMap<>();

        TransitEdge transitEdge = new TransitEdge(0, 1, 160);

        DistanceEdge distanceEdge1 = new DistanceEdge(1, 2, 1);
        DistanceEdge distanceEdge2 = new DistanceEdge(3, 4, 2);
        DistanceEdge distanceEdge5 = new DistanceEdge(5, 6, 5);
        DistanceEdge distanceEdge1_9 = new DistanceEdge(1, 9, 15);

        List<DistanceEdge> distanceEdges = new ArrayList<>(Arrays.asList(
                distanceEdge1,
                distanceEdge2,
                distanceEdge5,
                distanceEdge1_9
        ));

        allocateToClosest(allocations, transitEdge, distanceEdges);

        // transitEdge.start -> distanceEdge1.start
        // transitEdge.end -> distanceEdge1.end
        assertEquals(2, allocations.size());
        assertEquals(distanceEdge1.startGate(), (int) allocations.get(transitEdge.startFlight()));
        assertEquals(distanceEdge1.endGate(), (int) allocations.get(transitEdge.endFlight()));

        // distanceEdge and connected edges removed
        List<DistanceEdge> distanceEdgeRemoved = Arrays.asList(distanceEdge2, distanceEdge5);
        assertEquals(distanceEdgeRemoved, distanceEdges);
    }

    @Test
    public void test_toSymmetric_example() throws Exception {
        int[][] transitMatrix = {{0, 100, 6}, {60, 0, 8}, {4, 2, 0}};
        int[][] transitMatrixExpected = {{0, 160, 10}, {160, 0, 10}, {10, 10, 0}};

        assertArrayEquals(transitMatrixExpected, toSymmetric(transitMatrix));
    }

    @Test
    public void test_toSymmetric_alreadySymmetric() throws Exception {
        int[][] transitMatrix = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};

        assertArrayEquals(transitMatrix, toSymmetric(transitMatrix));
    }

    @Test
    public void test_totalDistanceWalked_example() {
        Map<Integer, Integer> allocations = new HashMap<>();
        allocations.put(2, 0);
        allocations.put(0, 1);
        allocations.put(1, 2);

        assertEquals(1020, totalDistanceWalked(allocations, exampleDistanceMatrix, exampleTransitMatrix));
    }

    @Test
    public void test_toOutputList() {
        List<Integer> output = Arrays.asList(3,1,2,4);

        Map<Integer, Integer> allocations = new HashMap<>();
        allocations.put(1,2);
        allocations.put(4,4);
        allocations.put(3,1);
        allocations.put(2,3);

        assertEquals(toOutputList(allocations), output);
    }

    @Test
    public void readDimensions(){
        Scanner scanner = new Scanner("3\n" +
                "0,10,12\n" +
                "10,0,5\n" +
                "12,5,0\n" +
                "0,100,6\n" +
                "60,0,8\n" +
                "4,2,0");
        assertEquals(Airport.readDimensions(scanner), 3);
        assertArrayEquals(Airport.readMatrix(scanner, 3),new int[][]{{0,10,12},{10,0,5},{12,5,0}});
        assertArrayEquals(Airport.readMatrix(scanner, 3),new int[][]{{0,100,6},{60,0,8},{4,2,0}});
    }
}
