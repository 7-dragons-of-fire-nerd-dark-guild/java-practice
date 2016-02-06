package com.sevendragons.hashcode2016.facade;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Facade {

    public static class Grid {
        private final int[][] cells;

        public Grid(int[][] cells) {
            this.cells = cells;
        }

        public Grid copy() {
            // TODO
            return null;
        }

        public static Grid fromScanner(Scanner scanner) {
            // TODO
            return null;
        }

        public static Grid fromString(String text) {
            return fromScanner(new Scanner(text));
        }
    }

    public interface Command {
        void apply(Grid grid);
    }

    public static class PaintSquare implements Command {
        final int row;
        final int col;
        final int size;

        public PaintSquare(int row, int col, int size) {
            this.row = row;
            this.col = col;
            this.size = size;
        }

        @Override
        public String toString() {
            return String.format("PAINT_SQUARE %s %s %s", row, col, size);
        }

        @Override
        public void apply(Grid grid) {
            // TODO
        }
    }

    public static class EraseCell implements Command {
        final int row;
        final int col;

        public EraseCell(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return String.format("ERASE_CELL %s %s", row, col);
        }

        @Override
        public void apply(Grid grid) {
            // TODO
        }
    }

    public static List<Command> generateCommands(Grid grid) {
        // TODO
        return null;
    }

    public static void solveForFile(String inputPath, String outputPath) throws IOException {
        Grid grid = Grid.fromScanner(new Scanner(new File(inputPath)));
        writeCommands(outputPath, generateCommands(grid));
    }

    private static void writeCommands(String outputPath, List<Command> commands) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputPath)))) {
            writer.write(String.format("%s\n", commands.size()));
            for (Command command : commands) {
                writer.write(String.format("%s\n", command));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        solveForFile(args[0], args[1]);
    }
}
