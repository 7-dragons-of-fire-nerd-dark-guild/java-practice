package com.sevendragons.hashcode2016.qualification;

import java.util.Scanner;

public class Qualification {

    static class Input {

        public static Input fromScanner(Scanner scanner) {
            return null;
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
