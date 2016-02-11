package com.sevendragons.hashcode2016.qualification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Qualification {

    static class Input {

        private static int rowNumber;
        private static int columnNumber;
        private static int droneNumber;
        private static int numberOfTurn;
        private static int maxPayload;
        private static int numberOfProductType;
        private static int numberOfWareHouse;
        private static Map<Integer, Warehouse> warehouseMap = new HashMap<>();
        private static Map<Integer, Product> productMap = new HashMap<>();
        private static Map<Integer, Order> orderMap = new HashMap<>();
        private static int numberOfCommand;

        public static Input fromScanner(Scanner scanner) {
            // Map info
            rowNumber = scanner.nextInt();
            columnNumber = scanner.nextInt();
            droneNumber = scanner.nextInt();
            numberOfTurn = scanner.nextInt();
            maxPayload = scanner.nextInt();
            scanner.nextLine();
            // Products
            numberOfProductType = scanner.nextInt();
            scanner.nextLine();
            for (int i=0; i<numberOfProductType; i++){
                productMap.put(i, parseProductType(scanner, i));
            }
            scanner.nextLine();
            // Warehouses
            numberOfWareHouse = scanner.nextInt();
            scanner.nextLine();
            for (int i =0; i< numberOfWareHouse; i++){
                warehouseMap.put(i, parseWareHouse(scanner,i));
            }
            //Commands
            int numberOfOrder = scanner.nextInt();
            scanner.nextLine();
            for (int i=0; i< numberOfOrder; i++){
                orderMap.put(i, parseOrder(scanner, i));
            }

            // I GIVE UP
            return null;
        }

        private static Product parseProductType(Scanner scanner, int id){
            Product product = new Product(id, scanner.nextInt());
            scanner.nextLine();
            return product;
        }

        private static Warehouse parseWareHouse(Scanner scanner, int id){
            int line = scanner.nextInt();
            int column = scanner.nextInt();
            scanner.nextLine();
            Map<Product, Integer> stock =  new HashMap<Product, Integer>();
            for (int productType = 0; productType < numberOfProductType; productType++){
                stock.put(productMap.get(productType), scanner.nextInt());
            }
            scanner.nextLine();
            return new Warehouse(id, line, column, new Products(stock));
        }

        private static Order parseOrder(Scanner scanner, int id){
            int line = scanner.nextInt();
            int column = scanner.nextInt();
            int numberOfItem = scanner.nextInt();
            int[] stockPerItems = new int[numberOfItem];
            scanner.nextLine();
            for (int i = 0; i < numberOfItem; i++){
                stockPerItems[i] = scanner.nextInt();
            }
            scanner.nextLine();
            return new Order(id, line, column);
        }

        public static Map<Integer, Product> getProductMap() {
            return productMap;
        }

        public static Map<Integer, Warehouse> getWarehouseMap() {
            return warehouseMap;
        }

        public static Map<Integer, Order> getOrderMap() {
            return orderMap;
        }

        public static int getNumberOfCommand() {
            return numberOfCommand;
        }

        public static int getNumberOfWareHouse() {
            return numberOfWareHouse;
        }

        public static int getNumberOfProductType() {
            return numberOfProductType;
        }

        public static int getNumberOfTurn() {
            return numberOfTurn;
        }

        public static int getMaxPayload() {
            return maxPayload;
        }

        public static int getDroneNumber() {
            return droneNumber;
        }

        public static int getRowNumber() {
            return rowNumber;
        }

        public static int getColumnNumber() {
            return columnNumber;
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

    abstract static class AbstractMapItem implements MapItem {
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
            if (products.products.containsKey(product)) {
                products.products.put(product, products.products.get(product) - quantity);
                if (products.products.get(product) <= 0) {
                    products.products.remove(product);
                }
            }
        }

        int getQuantity (Product product) {
            return products.products.get(product) != null ? products.products.get(product) : 0;
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

            List<Warehouse> warehouses = new ArrayList<>();

            for (Warehouse warehouse : warehouseMap.values()) {
                boolean satisfying = true;
                for (Product product : products.products.keySet()) {
                    if (warehouse.getQuantity(product) < products.products.get(product)) {
                        satisfying = false;
                    }
                }
                if (satisfying) {
                    warehouses.add(warehouse);
                    // Only one warehouse for now
                    break;
                }
            }

            return warehouses;
        }
    }

    static Map<Integer, Warehouse> warehouseMap = new HashMap<>();
    Map<Integer, Order> orderMap = new HashMap<>();

    int[][] warehouse2warehouse;
    int[][] order2order;
    int[][] order2warehouse;

    public Qualification(Input input) {
        order2order = createMatrix(orderMap, orderMap);
        warehouse2warehouse = createMatrix(warehouseMap, warehouseMap);
        order2warehouse = createMatrix(orderMap, warehouseMap);
    }

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

    static int calculateDistance(MapItem item1, MapItem item2) {
        int rowDiff = Math.abs(item1.getRow() - item2.getRow());
        int colDiff = Math.abs(item1.getCol() - item2.getCol());
        return (int) Math.ceil(Math.sqrt(rowDiff * rowDiff + colDiff * colDiff));
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
        Qualification system = new Qualification(input);
        return null;
    }

    public static void printOutput(Output output) {

    }

}
