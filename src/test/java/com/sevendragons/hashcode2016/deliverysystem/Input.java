package com.sevendragons.hashcode2016.deliverysystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Input {

    public final int rows;
    public final int cols;
    public final int turns;
    public final int maxPayload;

    public final List<Drone> drones;
    public final List<Product> products;
    public final List<Warehouse> warehouses;
    public final List<Order> orders;

    public Input(int rows, int cols, int turns, int maxPayload,
                 List<Drone> drones, List<Product> products,
                 List<Warehouse> warehouses, List<Order> orders) {
        this.rows = rows;
        this.cols = cols;
        this.turns = turns;
        this.maxPayload = maxPayload;
        this.drones = drones;
        this.products = products;
        this.warehouses = warehouses;
        this.orders = orders;
    }

    public static Input fromScanner(Scanner scanner) {
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int droneCount = scanner.nextInt();
        int turns = scanner.nextInt();
        int maxPayload = scanner.nextInt();

        int productTypeCount = scanner.nextInt();
        List<Product> products = new ArrayList<>();
        for (int id = 0; id < productTypeCount; id++) {
            products.add(id, new Product(id, scanner.nextInt()));
        }

        List<Drone> drones = new ArrayList<>();
        for (int id = 0; id < droneCount; ++id) {
            drones.add(new Drone(id, 0, 0, maxPayload, new Pack()));
        }

        int warehouseCount = scanner.nextInt();
        List<Warehouse> warehouses = new ArrayList<>();
        for (int id = 0; id < warehouseCount; id++) {
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            Pack pack = new Pack();
            for (int productId = 0; productId < productTypeCount; ++productId) {
                int count = scanner.nextInt();
                pack.add(products.get(productId), count);
            }

            Warehouse warehouse = new Warehouse(id, row, col, pack);
            warehouses.add(warehouse);
        }

        int orderCount = scanner.nextInt();
        List<Order> orders = new ArrayList<>();
        for (int id = 0; id < orderCount; id++) {
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            Pack pack = new Pack();
            int itemCount = scanner.nextInt();
            for (int count = 0; count < itemCount; ++count) {
                int productId = scanner.nextInt();
                pack.add(products.get(productId));
            }

            Order order = new Order(id, row, col, pack);
            orders.add(order);
        }

        return new Input(rows, cols, turns, maxPayload, drones, products, warehouses, orders);
    }

    public static Input fromPath(String path) throws FileNotFoundException {
        return fromScanner(new Scanner(new File(path)));
    }
}
