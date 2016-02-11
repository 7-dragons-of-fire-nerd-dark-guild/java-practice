package com.sevendragons.hashcode2016.qualification;

import java.util.*;

public class Qualification {

    static class Input {

        private final int rowNumber;
        private final int columnNumber;
        private final int droneNumber;
        private final int numberOfTurn;
        private final int maxPayload;
        private final int numberOfProductType;
        private final int numberOfWareHouse;
        private final Map<Integer, Warehouse> warehouseMap;
        private final Map<Integer, Product> productMap;
        private final Map<Integer, Order> orderMap;

        Input(int rowNumber, int columnNumber, int droneNumber, int numberOfTurn, int maxPayload,
              int numberOfProductType, int numberOfWareHouse, Map<Integer, Warehouse> warehouseMap,
              Map<Integer, Product> productMap, Map<Integer, Order> orderMap) {
            this.rowNumber = rowNumber;
            this.columnNumber = columnNumber;
            this.droneNumber = droneNumber;
            this.numberOfTurn = numberOfTurn;
            this.maxPayload = maxPayload;
            this.numberOfProductType = numberOfProductType;
            this.numberOfWareHouse = numberOfWareHouse;
            this.warehouseMap = warehouseMap;
            this.productMap = productMap;
            this.orderMap = orderMap;
        }

        public static Input fromScanner(Scanner scanner) {
            // Map info
            int rowNumber = scanner.nextInt();
            int columnNumber = scanner.nextInt();
            int droneNumber = scanner.nextInt();
            int numberOfTurn = scanner.nextInt();
            int maxPayload = scanner.nextInt();
            scanner.nextLine();

            // Products
            int numberOfProductType = scanner.nextInt();
            scanner.nextLine();
            Map<Integer, Product> productMap = new HashMap<>();
            for (int i = 0; i < numberOfProductType; i++) {
                productMap.put(i, parseProductType(scanner, i));
            }
            scanner.nextLine();

            // Warehouses
            int numberOfWareHouse = scanner.nextInt();
            scanner.nextLine();
            Map<Integer, Warehouse> warehouseMap = new HashMap<>();
            for (int i = 0; i < numberOfWareHouse; i++) {
                warehouseMap.put(i, parseWareHouse(scanner, i, numberOfProductType, productMap));
            }

            //Commands
            int numberOfOrder = scanner.nextInt();
            scanner.nextLine();
            Map<Integer, Order> orderMap = new HashMap<>();
            for (int i = 0; i < numberOfOrder; i++) {
                orderMap.put(i, parseOrder(scanner, i));
            }

            return new Input(rowNumber, columnNumber, droneNumber, numberOfTurn,
                    maxPayload, numberOfProductType,
                    numberOfWareHouse,
                    warehouseMap, productMap, orderMap);
        }

        private static Product parseProductType(Scanner scanner, int id){
            Product product = new Product(id, scanner.nextInt());
            scanner.nextLine();
            return product;
        }

        private static Warehouse parseWareHouse(
                Scanner scanner, int id, int numberOfProductType, Map<Integer, Product> productMap){
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
    }

    static class Output {
        private final List<Command> commands;

        public Output(List<Command> commands) {
            this.commands = commands;
        }
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

        public static Products fromOrder(Order order) {
            // TODO
            return new Products(Collections.emptyMap());
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

        void deliver(Order order, Products products) {

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

    abstract static class Command {
        final Drone drone;
        final char tag;

        Command(Drone drone, char tag) {
            this.drone = drone;
            this.tag = tag;
        }
    }

    abstract static class LoadDeliverCommand extends Command {
        final Warehouse warehouse;
        final Products products;

        LoadDeliverCommand(Drone drone, char tag, Warehouse warehouse, Products products) {
            super(drone, tag);
            this.warehouse = warehouse;
            this.products = products;
        }
    }

    static class LoadCommand extends LoadDeliverCommand {
        LoadCommand(Drone drone, Warehouse warehouse, Products products) {
            super(drone, 'L', warehouse, products);
        }
    }

    static class DeliverCommand extends LoadDeliverCommand {
        DeliverCommand(Drone drone, Warehouse warehouse, Products products) {
            super(drone, 'D', warehouse, products);
        }
    }

    static Map<Integer, Warehouse> warehouseMap = new HashMap<>();
    static Map<Integer, Order> orderMap = new HashMap<>();

    static int[][] warehouse2warehouse;
    static int[][] order2order;
    static int[][] order2warehouse;

    Map<Integer, Drone> droneMap = new HashMap<>();

    public Qualification(Input input) {
        warehouseMap.putAll(input.warehouseMap);
        orderMap.putAll(input.orderMap);
        warehouse2warehouse = createMatrix(warehouseMap, warehouseMap);
        order2order = createMatrix(orderMap, orderMap);
        order2warehouse = createMatrix(orderMap, warehouseMap);

        for (int i = 0; i < input.droneNumber; ++i) {
            droneMap.put(i, new Drone(i, 0, 0, input.maxPayload));
        }
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

        Drone pivot = system.droneMap.get(0);

        List<Command> commands = new ArrayList<>();
        while (true) {
            Pair<Order, Warehouse> pair = pivot.findNextOrderWarehouse();
            if (pair == null) {
                break;
            }
            Order order = pair.first;
            Warehouse warehouse = pair.second;

            Products products = Products.fromOrder(order);

            pivot.load(warehouse, products);
            commands.add(new LoadCommand(pivot, warehouse, products));

            pivot.deliver(order, products);
            commands.add(new DeliverCommand(pivot, warehouse, products));
        }

        return new Output(commands);
    }

    public static void printOutput(Output output) {
        // TODO
    }

}
