package com.sevendragons.hashcode2016.facade;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Facade {

    private static char MARKER_DONE = 'D';
    private static char MARKER_EMPTY = '.';
    private static char MARKER_PAINTED = '#';

    public static class Grid {
        protected final char[][] cells;

        public Grid(char[][] cells) {
            this.cells = cells;
        }

        public Grid copy() {
            int height=cells.length;
            int with = cells[0].length;
            char[][]cellsCopy = new char[height][with];
            for (int i = 0; i < height ; i ++){
                for (int j = 0 ; j < with ; j ++){
                    cellsCopy[i][j] = cells[i][j];
                }
            }
            return new Grid(cellsCopy);
        }

        public static Grid fromScanner(Scanner scanner) {
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();
            scanner.nextLine();
            char[][] cellz = new char[rows][columns];
            int row = 0;
            while(scanner.hasNextLine()) {
                cellz[row] = scanner.nextLine().toCharArray();
                row++;
            }
            return new Grid(cellz);
        }

        public static Grid fromString(String text) {
            return fromScanner(new Scanner(text));
        }

        public char[][] getCells(){
            return this.cells;
        }

        public int countCellsToPaint(int row, int column, int length, int width) {
            int count = 0;
            for (int i = row ; i < row + length ; i ++) {
                for (int j = column ; j < column + width ; j++) {
                    if (cells[i][j] == MARKER_PAINTED) {
                        count++;
                    }
                }
            }
            return count;
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
            for (int i = 0; i < size ; i++){
                for (int j = 0 ; j < size ; j++ ){
                    grid.cells[row+i][col+j]=1;
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PaintSquare that = (PaintSquare) o;

            if (row != that.row) return false;
            if (col != that.col) return false;
            return size == that.size;

        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + col;
            result = 31 * result + size;
            return result;
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
            grid.cells[row][col] = 0;
        }
    }

    public static class PaintLine implements Command {
        final int rowBegin;
        final int colBegin;
        final int rowEnd;
        final int colEnd;

        public PaintLine(int rowBegin, int colBegin,int rowEnd, int colEnd) {
            this.rowBegin = rowBegin;
            this.colBegin = colBegin;
            this.rowEnd = rowEnd;
            this.colEnd = colEnd;
        }

        @Override
        public String toString() {
            return String.format("PAINT_LINE %s %s %s %s", rowBegin, colBegin , rowEnd , colEnd);
        }

        @Override
        public void apply(Grid grid) {
            for (int i = rowBegin; i <= rowBegin + ( rowEnd - rowBegin ) ; i ++){
                for (int j = colBegin; j <= colBegin + ( colEnd - colBegin ) ; j ++){
                    grid.cells[i][j] = 1;
                }
            }
        }
    }

    public static List<Command> generateCommands(Grid grid) {

        List<Command> commands = new ArrayList<>();

        for (int i = 0 ; i < grid.cells.length ; i++) {
            for (int j = 0 ; j < grid.cells[i].length ; j++) {
                if (grid.cells[i][j] == '#') {
                    commands.add(new PaintSquare(i,j,0));
                }
            }
        }

        return commands;
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
