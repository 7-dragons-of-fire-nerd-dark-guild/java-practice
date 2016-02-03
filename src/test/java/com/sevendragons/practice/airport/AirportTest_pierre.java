package com.sevendragons.practice.airport;

import com.sevendragons.utils.graph.VisualizeGraph;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AirportTest_pierre extends Airport {

    @Test
    public void test_revert() {
        int[][] exampleDistanceMatrix = {
                {0, 10, 12},
                {10, 0, 5},
                {12, 5, 0}
        };
        int[][] exampleDistanceMatrixExpected = {
                {0, 10, 5},
                {10, 0, 12},
                {5, 12, 0}
        };
        assertArrayEquals(exampleDistanceMatrixExpected, revertMatrix(exampleDistanceMatrix));
    }

    @Test
    public void test_Average() {
        Scanner scanner = new Scanner("3\n" +
                "0,10,12\n" +
                "10,0,5\n" +
                "12,5,0\n" +
                "0,100,6\n" +
                "60,0,8\n" +
                "4,2,0");
        ;
        int[][] exampleDistanceMatrix = {
                {0, 10, 12},
                {10, 0, 5},
                {12, 5, 0}
        };
        int[][] exampleTransitMatrix = {
                {0, 100, 6},
                {60, 0, 8},
                {4, 2, 0}
        };
        assertEquals(1020, calculateScore(exampleDistanceMatrix, exampleTransitMatrix, executeAlgoAverage(scanner)));
    }
    @Test
    public void test_score(){
        int[][] exampleDistanceMatrix = {
                {0, 10, 12},
                {10, 0, 5},
                {12, 5, 0}
        };
        int[][] exampleTransitMatrix = {
                {0, 100, 6},
                {60, 0, 8},
                {4, 2, 0}
        };
        List<Integer> result = Arrays.asList(3,1, 2);
        assertEquals(1020,calculateScore(exampleDistanceMatrix,exampleTransitMatrix,result));


    }


    @Test
    public void test_Average_with_real_data() {
        try {
            File file = new File(AirportTest_pierre.class.getResource("data.txt").toURI());
            Scanner scanner = new Scanner(file);
            List<Integer> integers = executeAlgoAverage(scanner);
            scanner = new Scanner(file);
            assertEquals(3876330,calculateScore(scanner, integers));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }



    @Test
    public void visualize_real_data_distance() throws InterruptedException {
        try {
            File file = new File(AirportTest_pierre.class.getResource("data.txt").toURI());
            Scanner scanner = new Scanner(file);
            int dimensions = readDimensions(scanner);
            int[][] distanceMatrix = readMatrix(scanner, dimensions);
            VisualizeGraph.visualize(distanceMatrix, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void visualize_real_data_transit() throws InterruptedException {
        try {
            File file = new File(AirportTest_pierre.class.getResource("data.txt").toURI());
            Scanner scanner = new Scanner(file);
            int dimensions = readDimensions(scanner);
            readMatrix(scanner, dimensions);
            int[][] transitMatrix = readMatrix(scanner, dimensions);
            VisualizeGraph.visualize(toSymmetric(transitMatrix), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }




    @Test
    public void visualize_real_data_transit_with_revert() throws InterruptedException {
        try {
            File file = new File(AirportTest_pierre.class.getResource("data.txt").toURI());
            Scanner scanner = new Scanner(file);
            int dimensions = readDimensions(scanner);
            readMatrix(scanner, dimensions);
            int[][] transitMatrix = readMatrix(scanner, dimensions);
            VisualizeGraph.visualize(revertMatrix(toSymmetric(transitMatrix)), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }




    private int[][] revertMatrix(int[][] matrix) {
        int dimensions = matrix.length;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if(i!=j){
                    list.add(matrix[i][j]);
                }
            }
        }
        Collections.sort(list);

        int[][] matrixReversed = new int[dimensions][dimensions];
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if(i!=j) {
                    matrixReversed[i][j] = list.get(list.size() - 1 - list.indexOf(matrix[i][j]));
                }
            }
        }
        return matrixReversed;
    }


    private int calculateScore(Scanner scanner, List<Integer> integers) {
        int dimensions = readDimensions(scanner);
        int[][] matrixDistance = readMatrix(scanner, dimensions);
        int[][] matrixTransit = readMatrix(scanner, dimensions);
        return calculateScore(matrixDistance,matrixTransit,integers);
    }

     private int calculateScore(int[][] exampleDistanceMatrix, int[][] exampleTransitMatrix, List<Integer> result) {
         exampleTransitMatrix = toSymmetric(exampleTransitMatrix);
         int sum = 0;
         for (int i = 0; i < result.size();i++){
             for (int j = 1+i; j < result.size();j++) {
                sum += exampleTransitMatrix[i][j]*exampleDistanceMatrix[result.indexOf(i+1)][result.indexOf(j+1)];
             }
         }

         return sum;
     }



    //worst algo ever
    //3 876 330 :'(
    private List<Integer> executeAlgoAverage(Scanner scanner) {
        int dimensions = readDimensions(scanner);
        int[][] distanceMatrix = readMatrix(scanner, dimensions);
        int[][] transitMatrix = readMatrix(scanner, dimensions);

        //getting average
        Map<Integer, OptionalDouble> distanceAverage = calculateAverage(dimensions, distanceMatrix);
        Map<Integer, OptionalDouble> transitAverage = calculateAverage(dimensions, transitMatrix);


        //sort result

        List<Map.Entry<Integer, OptionalDouble>> sortedSetdistanceList = getSortedData(distanceAverage);
        List<Map.Entry<Integer, OptionalDouble>> sortedSetTransitList = getSortedData(transitAverage);


        // associate big transit with min
        Map<Integer, Integer> allocations = getAllocations(dimensions, sortedSetdistanceList, sortedSetTransitList);

        return toOutputList(dimensions, allocations);
    }

    private Map<Integer, Integer> getAllocations(int dimensions, List<Map.Entry<Integer, OptionalDouble>> sortedSetdistanceList, List<Map.Entry<Integer, OptionalDouble>> sortedSetTransitList) {
        Map<Integer, Integer> allocations = new HashMap<>();

        for (int i = 0; i < dimensions; i++) {
            allocations.put(sortedSetTransitList.get(dimensions - 1 - i).getKey(), sortedSetdistanceList.get(i).getKey());
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
        for (int i = 0; i < dimensions; i++) {
            distanceAverage.put(i, Arrays.stream(distanceMatrix[i]).average());
        }
        return distanceAverage;
    }
}
