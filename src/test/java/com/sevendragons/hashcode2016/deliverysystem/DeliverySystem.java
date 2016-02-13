package com.sevendragons.hashcode2016.deliverysystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DeliverySystem {

    public static class Output {
        private final List<Command> commands;

        public Output(List<Command> commands) {
            this.commands = commands;
        }
    }

    static Map<Integer, Warehouse> warehouseMap = new HashMap<>();
    static Map<Integer, Order> orderMap = new HashMap<>();

    static int[][] warehouse2warehouse;
    static int[][] order2order;
    static int[][] order2warehouse;

    Map<Integer, Drone> droneMap = new HashMap<>();

    public DeliverySystem(Input input) {
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

    public static void main(String[] args) throws FileNotFoundException {
        String inputPath = args[0];
//        String outputPath = args[1];

        Input input = parseInput(new Scanner(new File(inputPath)));
        Output output = solve(input);
        printOutput(output);
    }

    public static Input parseInput(Scanner scanner) {
        return Input.fromScanner(scanner);
    }

    public static Output solve(Input input) {
        DeliverySystem system = new DeliverySystem(input);

        Drone pivot = system.droneMap.get(0);

        List<Command> commands = new ArrayList<>();
        while (true) {
            Pair<Order, Warehouse> pair = pivot.findNextOrderWarehouse();
            if (pair == null) {
                break;
            }
            Order order = pair.first;
            Warehouse warehouse = pair.second;

            Pack pack = order.pack;

            pivot.load(warehouse, pack);
            commands.add(new LoadCommand(pivot, warehouse, pack));

            pivot.deliver(order);
            commands.add(new DeliverCommand(pivot, warehouse, pack));
        }

        return new Output(commands);
    }

    public static void printOutput(Output output) {
        System.out.println(output.commands.size());
        for (Command command : output.commands) {
            if (command.tag == 'D') {
                DeliverCommand deliverCommand = (DeliverCommand) command;
                for (Map.Entry<Product, Integer> entry : command.drone.pack.products.entrySet()) {
                    Product product = entry.getKey();
                    int quantity = entry.getValue();
                    System.out.printf("%d %c %d %d\n", deliverCommand.drone.id, deliverCommand.tag,
                            quantity, product.id);
                }
            }
            if (command.tag == 'L') {
                for (Map.Entry<Product, Integer> entry : command.drone.pack.products.entrySet()) {
                    Product product = entry.getKey();
                    int quantity = entry.getValue();
                    System.out.printf("%d %c %d %d\n", command.drone.id, command.tag,
                            quantity, product.id);
                }
            }
        }
    }

}
