package com.sevendragons.hashcode2016.qualification.pj;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by animus on 11/02/16.
 */
public class Utility {

    public static Model parseEntry(Scanner scanner){
        //Global
        int rowNumber = scanner.nextInt();
        int columnNumber = scanner.nextInt();
        int droneNumber = scanner.nextInt();
        int numberOfTurn = scanner.nextInt();
        int maxPayload = scanner.nextInt();
        scanner.nextLine();
        // Drones
        Map<Integer, Drone> droneMap = new HashMap<>();

        for (int i = 0; i< droneNumber; i++){
            droneMap.put(i, new Drone(i, 0, 0));
        }
        // Product Type
        int numberOfProductType = scanner.nextInt();
        scanner.nextLine();
        Map<Integer, ProductType> productTypeMap = new HashMap<>();
        for (int i=0; i<numberOfProductType; i++){
            productTypeMap.put(i, parseProductType(scanner, i));
        }
        // Warehouse
        int numberOfWarehouse = scanner.nextInt();
        scanner.nextLine();
        Map<Integer, WareHouse> wareHouseMap =  new HashMap<>();
        for (int i=0; i<numberOfWarehouse; i++){
            wareHouseMap.put(i, parseWareHouse(scanner, i, numberOfProductType));
            scanner.nextLine();
        }
        // Orders
        int numberOfOrders = scanner.nextInt();
        Map<Integer, Order> orderMap = new HashMap<>();
        scanner.nextLine();
        for (int i= 0; i< numberOfOrders; i++){
            orderMap.put(i, parseOrder(scanner, i));
            if (scanner.hasNextLine()){
                scanner.nextLine();
            }
        }
        Model model = new Model(rowNumber, columnNumber, numberOfTurn, maxPayload,
                productTypeMap, droneMap, wareHouseMap, orderMap);
        return model;

    }

    public static ProductType parseProductType(Scanner scanner, int id){
        ProductType productType = new ProductType(id, scanner.nextInt());
        return productType;
    }

    public static WareHouse parseWareHouse(Scanner scanner, int id, int numberOfProductType){
        int line = scanner.nextInt();
        int column = scanner.nextInt();
        scanner.nextLine();
        Map<Integer, Integer> stock =  new HashMap<Integer, Integer>();
        for (int productType = 0; productType < numberOfProductType; productType++) {
            stock.put(productType, scanner.nextInt());
        }
        return new WareHouse(id, line, column, stock);
    }

    public static Order parseOrder(Scanner scanner, int id){
        int line = scanner.nextInt();
        int column = scanner.nextInt();
        scanner.nextLine();
        int numberOfItem = scanner.nextInt();
        Map<Integer, Integer> stock = new HashMap<>();
        scanner.nextLine();
        for (int i = 0; i < numberOfItem; i++){
            stock.put(i, scanner.nextInt());
        }
        return new Order(id, line, column, stock);
    }
}
