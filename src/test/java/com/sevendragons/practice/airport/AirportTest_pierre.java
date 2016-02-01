package com.sevendragons.practice.airport;

import com.sevendragons.utils.graph.VisualizeGraph;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.IntStream;

public class AirportTest_pierre extends Airport{

    @Test
    public void test_with_real_data(){
        try {
            File file = new File(AirportTest_pierre.class.getResource("data.txt").toURI());
            Scanner scanner = new Scanner(file);
            executeAlgoAverage(scanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void visualize_real_data() throws InterruptedException {
        try {
            File file = new File(AirportTest_pierre.class.getResource("data.txt").toURI());
            Scanner scanner = new Scanner(file);
            int dimensions = readDimensions(scanner);
            int[][] distanceMatrix = readMatrix(scanner, dimensions);
            VisualizeGraph.visualize(distanceMatrix,null);
            Thread.sleep(10000);
            int[][] transitMatrix = readMatrix(scanner, dimensions);
            VisualizeGraph.visualize(toSymmetric(transitMatrix),null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Thread.sleep(10000);

    }

    @Test
    public void test(){
        Scanner scanner = new Scanner("3\n" +
                "0,10,12\n" +
                "10,0,5\n" +
                "12,5,0\n" +
                "0,100,6\n" +
                "60,0,8\n" +
                "4,2,0");
        executeAlgoAverage(scanner);
    }


    //worst algo ever
    //3 876 330 :'(
    private void executeAlgoAverage(Scanner scanner) {
        int dimensions = readDimensions(scanner);
        int[][] distanceMatrix = readMatrix(scanner, dimensions);
        int[][] transitMatrix = readMatrix(scanner, dimensions);

        //getting average
        Map<Integer, OptionalDouble> distanceAverage = calculateAverage(dimensions, distanceMatrix);
        Map<Integer, OptionalDouble> transitAverage = calculateAverage(dimensions, transitMatrix);


        //sort result

        List<Map.Entry<Integer, OptionalDouble>> sortedSetdistanceList = getSortedData(distanceAverage);
        List<Map.Entry<Integer,OptionalDouble>>sortedSetTransitList = getSortedData(transitAverage);


        // associate big transit with min
        Map<Integer, Integer> allocations = getAllocations(dimensions, sortedSetdistanceList, sortedSetTransitList);

        List<Integer> output = toOutputList(dimensions,allocations);
        writeOutput(output);
    }

    private Map<Integer, Integer> getAllocations(int dimensions, List<Map.Entry<Integer, OptionalDouble>> sortedSetdistanceList, List<Map.Entry<Integer, OptionalDouble>> sortedSetTransitList) {
        Map<Integer, Integer> allocations = new HashMap<>();

        for (int i = 0; i < dimensions ; i++){
            allocations.put(sortedSetTransitList.get(dimensions-1-i).getKey(),sortedSetdistanceList.get(i).getKey());
        }
        assert allocations.size() == dimensions;
        return allocations;
    }

    private List<Map.Entry<Integer, OptionalDouble>> getSortedData(Map<Integer, OptionalDouble> distanceAverage) {

        ArrayList<Map.Entry<Integer, OptionalDouble>> entries = new ArrayList<>(distanceAverage.entrySet());
        Collections.sort(entries, (o1, o2) -> {
            return Double.compare(o1.getValue().getAsDouble(), o2.getValue().getAsDouble());
        });


        return entries;
    }

    private Map<Integer, OptionalDouble> calculateAverage(int dimensions, int[][] distanceMatrix) {
        HashMap<Integer, OptionalDouble> distanceAverage = new HashMap<>();
        for (int i = 0; i<dimensions;i++){
            distanceAverage.put(i, Arrays.stream(distanceMatrix[i]).average());
        }
        return distanceAverage;
    }
}
