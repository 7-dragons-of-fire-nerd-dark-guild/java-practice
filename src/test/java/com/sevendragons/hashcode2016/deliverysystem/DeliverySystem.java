package com.sevendragons.hashcode2016.deliverysystem;

import com.sevendragons.hashcode2016.deliverysystem.solver.Solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DeliverySystem {

    static Map<Integer, Warehouse> warehouseMap = new HashMap<>();
    static Map<Integer, Order> orderMap = new HashMap<>();

    static int[][] warehouse2warehouse;
    static int[][] order2order;
    static int[][] order2warehouse;

    Map<Integer, Drone> droneMap = new HashMap<>();

    static int[][] createMatrix(Map<Integer, ? extends MapItem> from, Map<Integer, ? extends MapItem> to) {
        int fromDim = from.size();
        int toDim = to.size();

        int[][] matrix = new int[fromDim][];
        for (int i = 0; i < fromDim; ++i) {
            matrix[i] = new int[toDim];
        }

        for (int i = 0; i < fromDim; ++i) {
            for (int j = 0; j < toDim; ++j) {
                int distance = calculateDistance(from.get(i), to.get(j));
                matrix[i][j] = distance;
                //matrix[j][i] = distance;
            }
        }

        return matrix;
    }

    public static int calculateDistance(MapItem item1, MapItem item2) {
        int rowDiff = Math.abs(item1.getRow() - item2.getRow());
        int colDiff = Math.abs(item1.getCol() - item2.getCol());
        return (int) Math.ceil(Math.sqrt(rowDiff * rowDiff + colDiff * colDiff));
    }

    public static Input parseInput(String inputPath) throws FileNotFoundException {
        return Input.fromScanner(new Scanner(new File(inputPath)));
    }

    public static void solveAndWriteOutput(String inputPath, String outputPath, Solver solver) throws IOException {
        Input input = parseInput(inputPath);
        Output output = solver.solve(input);
        writeOutput(new File(outputPath), output);
    }

    public static void writeOutput(File file, Output output) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            for (String line : output.toOutputLines()) {
                writer.append(line);
            }
        }
    }
}
