package com.sevendragons.practice.airport;

import java.util.*;

public class Airport {
    public static class Edge {
        protected final int start;
        protected final int end;
        protected final int value;

        public Edge(int start, int end, int value) {
            this.start = start;
            this.end = end;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("%s -> %s : %s", start, end, value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Edge edge = (Edge) o;

            if (start != edge.start) {
                return false;
            }
            if (end != edge.end) {
                return false;
            }
            return value == edge.value;

        }

        @Override
        public int hashCode() {
            int result = start;
            result = 31 * result + end;
            result = 31 * result + value;
            return result;
        }
    }

    public static class TransitEdge extends Edge {
        public TransitEdge(int start, int end, int value) {
            super(start, end, value);
        }

        public int startFlight() {
            return start;
        }

        public int endFlight() {
            return end;
        }

        public int passengers() {
            return value;
        }
    }

    public static class DistanceEdge extends Edge {
        public DistanceEdge(int start, int end, int value) {
            super(start, end, value);
        }

        public int startGate() {
            return start;
        }

        public int endGate() {
            return end;
        }

        public int distance() {
            return value;
        }
    }

    public static List<DistanceEdge> toDistanceEdges(int[][] distanceMatrix) {
        List<DistanceEdge> distanceEdges = new ArrayList<>();
        for (int row = 0; row < distanceMatrix.length; ++row) {
            for (int col = 0; col < distanceMatrix[row].length; ++col) {
                if (row != col) {
                    distanceEdges.add(new DistanceEdge(row, col, distanceMatrix[row][col]));
                }
            }
        }
        return distanceEdges;
    }

    public static List<TransitEdge> toTransitEdges(int[][] transitMatrix) {
        List<TransitEdge> transitEdges = new ArrayList<>();
        for (int row = 0; row < transitMatrix.length; ++row) {
            for (int col = 0; col < transitMatrix[row].length; ++col) {
                if (row != col) {
                    transitEdges.add(new TransitEdge(row, col, transitMatrix[row][col]));
                }
            }
        }
        return transitEdges;
    }

    public static void sortByPassengersDescending(List<TransitEdge> transitEdges) {
        Collections.sort(transitEdges, (o1, o2) -> -Integer.compare(o1.passengers(), o2.passengers()));
    }

    public static void sortByDistanceAscending(List<DistanceEdge> distanceEdges) {
        Collections.sort(distanceEdges, (o1, o2) -> Integer.compare(o1.distance(), o2.distance()));
    }

    public static void main(String[] args) {
        // Pierre-Jean
        Scanner scanner = new Scanner("3\n" +
                "0,10,12\n" +
                "10,0,5\n" +
                "12,5,0\n" +
                "0,100,6\n" +
                "60,0,8\n" +
                "4,2,0");
        int dimensions = readDimensions(scanner);
        int[][] distanceMatrix = readMatrix(scanner, dimensions);

        // Pierre
        int[][] transitMatrix = toSymmetric(readMatrix(scanner, dimensions));

        // Janos
        List<TransitEdge> transitEdges = toTransitEdges(transitMatrix);
        sortByPassengersDescending(transitEdges);

        List<DistanceEdge> distanceEdges = toDistanceEdges(distanceMatrix);
        sortByDistanceAscending(distanceEdges);

        Map<Integer, Integer> allocations = new HashMap<>();
        for (TransitEdge transit : transitEdges) {
            if (!isAllocated(allocations, transit.start) && !isAllocated(allocations, transit.end)) {
                allocateToClosest(allocations, transit, distanceEdges);
                // take the first distance edge
                // allocate transit.start -> edge.start
                // allocate transit.end -> edge.end
                // remove edge from list, and all edges where start == edge.start or .end
            } else if (!isAllocated(allocations, transit.start)) { // end was allocated
                int flight = transit.startFlight();
                int gate = allocations.get(transit.endFlight());
                allocateToClosest(allocations, flight, gate, distanceMatrix, distanceEdges);
            } else if (!isAllocated(allocations, transit.end)) { // start was allocated
                int flight = transit.endFlight();
                int gate = allocations.get(transit.startFlight());
                allocateToClosest(allocations, flight, gate, distanceMatrix, distanceEdges);
            } else {
                // both start and end have been allocated, cannot do anything anymore for this transit
            }
        }
        assert allocations.size() == dimensions;

        // Martin
        List<Integer> output = toOutputList(allocations);
        writeOutput(output);
    }

    public static void writeOutput(List<Integer> output) {
        output.forEach(System.out::println);
    }

    public static List<Integer> toOutputList(Map<Integer, Integer> allocations){
        Map<Integer, Integer> sortedAlloc = new TreeMap<>();
        for (Map.Entry<Integer, Integer> allocation : allocations.entrySet()) {
            sortedAlloc.put(allocation.getValue(), allocation.getKey() + 1);
        }
        return new ArrayList<>(sortedAlloc.values());
    }

    public static boolean isAllocated(Map<Integer, Integer> allocations, int flight) {
        return allocations.containsKey(flight);
    }

    private static void removeConnectedDistanceEdges(List<DistanceEdge> distanceEdges, Collection<Integer> gates) {
        Iterator<DistanceEdge> iterator = distanceEdges.iterator();
        while (iterator.hasNext()) {
            DistanceEdge edge = iterator.next();
            if (gates.contains(edge.startGate()) || gates.contains(edge.endGate())) {
                iterator.remove();
            }
        }
    }

    public static void allocateToClosest(
            Map<Integer, Integer> allocations, TransitEdge transit, List<DistanceEdge> distanceEdges) {
        // assumption: distanceEdges is sorted ascending by distance,
        // so the first entry is the closes pair of gates
        DistanceEdge distanceEdge = distanceEdges.remove(0);
        allocations.put(transit.startFlight(), distanceEdge.startGate());
        allocations.put(transit.endFlight(), distanceEdge.endGate());

        removeConnectedDistanceEdges(distanceEdges, Arrays.asList(transit.startFlight(), transit.endFlight()));
    }

    public static void allocateToClosest(
            Map<Integer, Integer> allocations, int flight, int gate,
            int[][] distanceMatrix, List<DistanceEdge> distanceEdges) {

        int closestGate = findClosestGate(distanceMatrix, gate, allocations);
        allocations.put(flight, closestGate);

        removeConnectedDistanceEdges(distanceEdges, Collections.singleton(closestGate));
    }

    private static int findClosestGate(int[][] distanceMatrix, int gate, Map<Integer, Integer> allocations) {
        int minDistance = Integer.MAX_VALUE;
        int minDistanceGate = 0;
        for (int otherGate = 0; otherGate < distanceMatrix.length; ++otherGate) {
            if (otherGate != gate && !isAllocatedGate(allocations, otherGate)) {
                int distance = distanceMatrix[gate][otherGate];
                if (distance < minDistance) {
                    minDistance = distance;
                    minDistanceGate = otherGate;
                }
            }
        }
        return minDistanceGate;
    }

    private static boolean isAllocatedGate(Map<Integer, Integer> allocations, int gate) {
        return allocations.containsValue(gate);
    }

    public static int[][] toSymmetric(int[][] matrix) {
        int matrixLength = matrix.length;
        int[][] matrixSymmetric = new int[matrixLength][matrixLength];

        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                if (j != i) {
                    matrixSymmetric[i][j] = matrix[i][j] + matrix[j][i];
                } else {
                    matrixSymmetric[i][j] = matrix[i][j];
                }
            }
        }

        return matrixSymmetric;
    }

    public static int[][] readMatrix(Scanner scanner, int dimensions) {
        int[][] matrix = new int[dimensions][dimensions];
        for (int i = 0; i < dimensions; i++) {
            String[] values = scanner.nextLine().split(",");
            for (int j = 0; j < values.length; j++) {
                matrix[i][j] = Integer.valueOf(values[j]);
            }
        }
        return matrix;
    }

    public static int readDimensions(Scanner scanner) {
        return Integer.valueOf(scanner.nextLine());
    }

    public static int totalDistanceWalked(
            Map<Integer, Integer> allocations, int[][] distanceMatrix, int[][] transitMatrix) {
        int total = 0;
        for (int row = 0; row < transitMatrix.length; ++row) {
            for (int col = 0; col < transitMatrix[row].length; ++col) {
                if (row != col) {
                    total += distanceWalked(allocations, distanceMatrix, transitMatrix, row, col);
                }
            }
        }
        return total;
    }

    private static int distanceWalked(
            Map<Integer, Integer> allocations, int[][] distanceMatrix, int[][] transitMatrix,
            int startFlight, int endFlight) {

        Integer startGate = allocations.get(startFlight);
        if (startGate == null) {
            return 0;
        }

        Integer endGate = allocations.get(endFlight);
        if (endGate == null) {
            return 0;
        }

        return distanceMatrix[startGate][endGate] * transitMatrix[startFlight][endFlight];
    }
}
