package com.sevendragons.hashcode2016.qualification;

import java.util.HashMap;
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
        private static int[] productWeight;
        private static int numberOfWareHouse;
        private static Map<Integer, WareHouse> warehouses = new HashMap<>();
        private static int numberOfCommand;

        public static Input fromScanner(Scanner scanner) {
            rowNumber = scanner.nextInt();
            columnNumber = scanner.nextInt();
            droneNumber = scanner.nextInt();
            numberOfTurn = scanner.nextInt();
            maxPayload = scanner.nextInt();
            scanner.nextLine();
            numberOfProductType = scanner.nextInt();
            scanner.nextLine();
            productWeight = new int[numberOfProductType];
            for (int i=0; i<numberOfProductType; i++){
                productWeight[i] = scanner.nextInt();
            }
            scanner.nextLine();
            numberOfWareHouse = scanner.nextInt();
            for (int i =0; i< numberOfWareHouse; i++){
                warehouses.put(i, parseWareHouse(scanner,i));
            }
            int numberOfCommand = scanner.nextInt()
            for (int i=0; i< numberOfCommand; i++){
                parseOrder(scanner, i);
            }



        }

        private static WareHouse parseWareHouse(Scanner scanner, int id){
            int line = scanner.nextInt();
            int column = scanner.nextInt();
            scanner.nextLine();
            Map<Integer, Integer> stock =  new HashMap<Integer, Integer>();
            for (int productType = 0; productType < numberOfProductType; productType++){
                stock.put(productType, scanner.nextInt());
            }
            scanner.nextLine();
            return new WareHouse(id, line, column, stock);
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
            return new Order(line, column, stockPerItems);
        }
    }

    static class Output {

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
