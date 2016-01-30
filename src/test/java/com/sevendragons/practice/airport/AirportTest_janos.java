package com.sevendragons.practice.airport;

import com.sevendragons.practice.airport.Airport.DistanceEdge;
import com.sevendragons.practice.airport.Airport.TransitEdge;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class AirportTest_janos {
    private static void allocateToClosest(
            Map<Integer, Integer> allocations, TransitEdge transit, List<DistanceEdge> distanceEdges) {

    }

    @Test
    public void test_allocate_to_closest() {
        Map<Integer, Integer> allocations = new HashMap<>();

        TransitEdge transitEdge = new TransitEdge(0, 1, 160);

        DistanceEdge distanceEdge1 = new DistanceEdge(1, 2, 1);
        DistanceEdge distanceEdge2 = new DistanceEdge(3, 4, 2);
        DistanceEdge distanceEdge5 = new DistanceEdge(5, 6, 5);

        List<DistanceEdge> distanceEdges = new ArrayList<>(Arrays.asList(
                distanceEdge1,
                distanceEdge2,
                distanceEdge5
        ));

        allocateToClosest(allocations, transitEdge, distanceEdges);

        // transitEdge.start -> distanceEdge1.start
        // transitEdge.end -> distanceEdge1.end
        assertEquals(2, allocations.size());
        assertEquals(distanceEdge1.startGate(), (int) allocations.get(transitEdge.startFlight()));
        assertEquals(distanceEdge1.endGate(), (int) allocations.get(transitEdge.endFlight()));

        // distanceEdge removed
        List<DistanceEdge> distanceEdgeRemoved = Arrays.asList(distanceEdge2, distanceEdge5);
        assertEquals(distanceEdgeRemoved, distanceEdges);
    }

}
