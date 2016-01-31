package com.sevendragons.practice.airport;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

public class AirportTest_pierre extends Airport{



    @Test
    public void test(){
        Scanner scanner = new Scanner("3\n" +
                "0,10,12\n" +
                "10,0,5\n" +
                "12,5,0\n" +
                "0,100,6\n" +
                "60,0,8\n" +
                "4,2,0");
        int dimensions = readDimensions(scanner);
        int[][] distanceMatrix = readMatrix(scanner, dimensions);

        //getting average
        SortedMap<Integer, OptionalDouble> distanceAverage = new TreeMap<>();
        for (int i = 0; i<dimensions;i++){
            distanceAverage.put(i, Arrays.stream(distanceMatrix[i]).average());
        }

        SortedMap<Integer, OptionalDouble> transitAverage = new TreeMap<>();
        int[][] transitMatrix = readMatrix(scanner, dimensions);
        for (int i = 0; i<dimensions;i++){
            transitAverage.put(i,Arrays.stream(transitMatrix[i]).average());
        }


        //sort result

        TreeSet<Map.Entry<Integer,OptionalDouble>> sortedSetdistance = new TreeSet<>((o1, o2) -> {
            return Double.compare(o1.getValue().getAsDouble(), o2.getValue().getAsDouble());
        });
        sortedSetdistance.addAll(distanceAverage.entrySet());
        TreeSet<Map.Entry<Integer,OptionalDouble>> sortedSetTransit = new TreeSet<>((o1, o2) -> {
            return Double.compare(o1.getValue().getAsDouble(), o2.getValue().getAsDouble());
        });
        sortedSetTransit.addAll(transitAverage.entrySet());

        // associate big transit with min
        Map<Integer, Integer> allocations = new HashMap<>();
        List<Map.Entry<Integer,OptionalDouble>>sortedSetdistanceList = new ArrayList<>(sortedSetdistance);
        List<Map.Entry<Integer,OptionalDouble>>sortedSetTransitList = new ArrayList<>(sortedSetTransit);
        for (int i = 0; i < dimensions ; i++){
            allocations.put(sortedSetTransitList.get(dimensions-1-i).getKey(),sortedSetdistanceList.get(i).getKey());
        }






        assert allocations.size() == dimensions;

        List<Integer> output = toOutputList(dimensions,allocations);
        writeOutput(output);
    }
}
