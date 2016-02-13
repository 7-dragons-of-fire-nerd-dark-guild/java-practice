package com.sevendragons.hashcode2016.deliverysystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Input {

    public final int rowNumber;
    public final int columnNumber;
    public final int droneNumber;
    public final int numberOfTurn;
    public final int maxPayload;
    public final int numberOfProductType;
    public final int numberOfWareHouse;

    public final Map<Integer, Warehouse> warehouseMap;
    public final Map<Integer, Product> productMap;
    public final Map<Integer, Order> orderMap;

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
        return new Product(id, scanner.nextInt());
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
        return new Warehouse(id, line, column, new Pack(stock));
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
