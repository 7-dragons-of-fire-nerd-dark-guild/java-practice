package com.sevendragons.hashcode2016.qualification;

import java.util.*;

public class Qualification {

    static class Input {

        public static Input fromScanner(Scanner scanner) {
            return null;
        }
    }

    static class Output {

    }

    static class Pair<T, U> {
        final T first;
        final U second;

        Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }

    static class Products {
        final Map<Product, Integer> products;

        Products(Map<Product, Integer> products) {
            this.products = products;
        }
    }

    static class Product {
        final int id;
        final int weight;

        Product(int id, int weight) {
            this.id = id;
            this.weight = weight;
        }
    }

    interface MapItem {
        int getId();
        int getRow();
        int getCol();
    }

    abstract static class AbstractMapItem {
        int id;
        int row;
        int col;

        AbstractMapItem(int id, int row, int col) {
            this.id = id;
            this.row = row;
            this.col = col;
        }

        public int getId() {
            return id;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }

    static class Warehouse extends AbstractMapItem {
        final Products products;

        Warehouse(int id, int row, int col, Products products) {
            super(id, row, col);
            this.products = products;
        }

        // remove from warehouse
        void load(Product product, int quantity) {

        }
    }

    static class Drone extends AbstractMapItem {
        final int maxWeight;

        Products products;
        List<Order> schedule;

        Drone(int id, int row, int col, int maxWeight) {
            super(id, row, col);
            this.maxWeight = maxWeight;
        }

        int calcDistanceToWarehouse(Warehouse warehouse) {
            return 0;
        }

        int calcDistanceToOrder(Order warehouse) {
            return 0;
        }

        void load(Warehouse warehouse, Products products) {

        }

        void unload(Order order, Products products) {

        }

        Pair<Order, Warehouse> findNextOrderWarehouse() {
            return null;
        }

    }

    static class Order extends AbstractMapItem {
        Products products;
        boolean completed;

        Order(int id, int row, int col) {
            super(id, row, col);
        }

        List<Warehouse> findWarehouses() {
            return Collections.emptyList();
        }
    }

    Map<Integer, Warehouse> warehouseMap = new HashMap<>();
    Map<Integer, Order> orderMap = new HashMap<>();

    int[][] warehouse2warehouse;
    int[][] order2order;
    int[][] order2warehouse;

    int[][] createMatrix(Map<Integer, MapItem> mapItems) {
        return null;
    }

    int calculateDistance(MapItem item1, MapItem item2) {
        return 0;
    }

    public static void main(String[] args) {
        String inputPath = args[0];
        String outputPath = args[1];

        Input input = parseInput(new Scanner(inputPath));
        Output output = solve(input);
        printOutput(output);
    }

    public static Input parseInput(Scanner scanner) {
        return Input.fromScanner(scanner);
    }

    public static Output solve(Input input) {
        return null;
    }

    public static void printOutput(Output output) {

    }

}
