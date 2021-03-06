package com.sevendragons.hashcode2016.facade;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Facade {

    public static final char MARKER_DONE = 'D';
    public static final char MARKER_EMPTY = '.';
    public static final char MARKER_PAINTED = '#';

    public static class Grid {
        protected final char[][] cells;

        public Grid(char[][] cells) {
            this.cells = cells;
        }

        public Grid copy() {
            int height = cells.length;
            int with = cells[0].length;
            char[][] cellsCopy = new char[height][with];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < with; j++) {
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
            while (scanner.hasNextLine()) {
                cellz[row] = scanner.nextLine().toCharArray();
                row++;
            }
            return new Grid(cellz);
        }

        public static Grid fromString(String text) {
            return fromScanner(new Scanner(text));
        }

        public char[][] getCells() {
            return this.cells;
        }

        public int countCellsToPaint(int row, int column, int length, int width) {
            int count = 0;
            for (int i = row; i < row + length; i++) {
                for (int j = column; j < column + width; j++) {
                    if (cells[i][j] == MARKER_PAINTED) {
                        count++;
                    }
                }
            }
            return count;
        }

        public int findBestBrush(int row, int col) {
            int size = 0;
            while (isGoodSize(row, col, size + 1)) {
                ++size;
            }
            return size;
        }

        private boolean isGoodSize(int row, int col, int size) {
            int dim = 2 * size + 1;
            int height = Math.min(dim, cells.length - row);
            int width = Math.min(dim, cells[row].length - col);
            int count = countCellsToPaint(row, col, height, width);
            return count > dim * dim / 2 + 1;
        }

        public List<Command> generateCommands(int row, int col, int size) {
            List<Command> commands = new ArrayList<>();

            commands.add(new PaintSquare((size + row), (size + col), size));

            int dim = 2 * size + 1;
            int height = Math.min(dim, cells.length - row);
            int width = Math.min(dim, cells[row].length - col);
            for (int i = row; i < row + height; i++) {
                for (int j = col; j < col + width; j++) {
                    if (cells[i][j] == MARKER_EMPTY) {
                        commands.add(new EraseCell(i, j));
                    }
                }
            }

            return commands;
        }

        public void clearArea(int row, int col, int size) {
            for (int i = row; i < row + size; i++) {
                for (int j = col; j < col + size; j++) {
                    cells[i][j] = MARKER_DONE;
                }
            }
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
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    grid.cells[row + i][col + j] = MARKER_DONE;
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
            grid.cells[row][col] = MARKER_EMPTY;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EraseCell that = (EraseCell) o;

            if (row != that.row) return false;
            if (col != that.col) return false;
            return true;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + col;
            return result;
        }
    }

    public static class PaintLine implements Command {
        final int rowBegin;
        final int colBegin;
        final int rowEnd;
        final int colEnd;

        public PaintLine(int rowBegin, int colBegin, int rowEnd, int colEnd) {
            this.rowBegin = rowBegin;
            this.colBegin = colBegin;
            this.rowEnd = rowEnd;
            this.colEnd = colEnd;
        }

        @Override
        public String toString() {
            return String.format("PAINT_LINE %s %s %s %s", rowBegin, colBegin, rowEnd, colEnd);
        }

        @Override
        public void apply(Grid grid) {
            for (int i = rowBegin; i <= rowBegin + (rowEnd - rowBegin); i++) {
                for (int j = colBegin; j <= colBegin + (colEnd - colBegin); j++) {
                    grid.cells[i][j] = MARKER_DONE;
                }
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PaintLine that = (PaintLine) o;

            if (rowBegin != that.rowBegin) return false;
            if (colBegin != that.colBegin) return false;
            if (rowEnd != that.rowEnd) return false;
            if (colEnd != that.colEnd) return false;
            return true;
        }

        @Override
        public int hashCode() {
            int result = rowBegin;
            result = 31 * result + colBegin;
            result = 31 * result + rowBegin;
            result = 31 * result + colEnd;
            return result;
        }
    }

    public static List<Command> generateCommands(Grid grid) {
        return generateCommandsFromSquares(grid);
    }

    public static List<Command> generateCommands_dumest(Grid grid) {

        List<Command> commands = new ArrayList<>();
        int height = grid.cells.length;
        int width = grid.cells[0].length;
        Grid gridCopy = optimiseAlgoLine(commands, grid.copy());

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid.cells[i][j] == MARKER_PAINTED && gridCopy.cells[i][j] != MARKER_DONE) {
                    //checking if line
                    int colJ = j;
                    while (grid.cells[i][colJ] == MARKER_PAINTED) {
                        colJ++;
                        if (colJ == width) {
                            break;
                        }
                    }
                    //checking if col
                    int rowI = i;
                    while (grid.cells[rowI][j] == MARKER_PAINTED) {
                        rowI++;
                        if (rowI == height) {
                            break;
                        }
                    }
                    if (colJ - j > 1 && colJ - j >= rowI - i) {
                        colJ--;
                        PaintLine paintLine = new PaintLine(i, j, i, colJ);
                        paintLine.apply(gridCopy);
                        commands.add(paintLine);
                        j = colJ;
                    } else if (rowI - i > 1) {
                        rowI--;
                        PaintLine paintLine = new PaintLine(i, j, rowI, j);
                        paintLine.apply(gridCopy);
                        commands.add(paintLine);
                    } else {
                        commands.add(new PaintSquare(i, j, 0));
                    }

                }
            }
        }

        return commands;
    }

    public static Grid optimiseAlgoLine(List<Command> commands, Grid grid) {
        int height = grid.cells.length;
        int width = grid.cells[0].length;
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width-1; j++) {
                if (grid.cells[i][j] == MARKER_EMPTY) {
                    if (grid.cells[i - 1][j] == MARKER_PAINTED && grid.cells[i + 1][j] == MARKER_PAINTED &&
                            grid.cells[i][j - 1] == MARKER_PAINTED && grid.cells[i][j + 1] == MARKER_PAINTED) {

                        int rowBegin=i-1,colBegin=j-1,rowEnd=i+1,colEnd=j+1;
                        //lets do the col optimisation

                        for (int optimizeI = i-1; optimizeI > 0; optimizeI--) {
                            if(grid.cells[optimizeI][j] == MARKER_EMPTY){
                                break;
                            }
                            rowBegin = optimizeI;
                        }
                        for (int optimizeI = i+1; optimizeI < height; optimizeI++) {
                            if(grid.cells[optimizeI][j] == MARKER_EMPTY){
                                break;
                            }
                            rowEnd = optimizeI;
                        }
                        PaintLine paintLine = new PaintLine(rowBegin, j, rowEnd, j);
                        paintLine.apply(grid);
                        commands.add(paintLine);


                        //lets do the row optimisation

                        for (int optimizeJ = j-1; optimizeJ > 0; optimizeJ--) {
                            if(grid.cells[i][optimizeJ] == MARKER_EMPTY){
                                break;
                            }
                            colBegin = optimizeJ;
                        }
                        for (int optimizeJ = j+1; optimizeJ < width; optimizeJ++) {
                            if(grid.cells[i][optimizeJ] == MARKER_EMPTY){
                                break;
                            }
                            colEnd = optimizeJ;
                        }
                        paintLine = new PaintLine(i, colBegin, i, colEnd);
                        paintLine.apply(grid);
                        commands.add(paintLine);

                        //lets do the action erase
                        EraseCell eraseCell = new EraseCell(i, j);
                        eraseCell.apply(grid);
                        commands.add(eraseCell);
                    }
                }
            }
        }

        return grid;
    }

    public static List<Facade.Command> generateCommandsFromSquares(Facade.Grid grid) {
        List<Facade.Command> commands = new ArrayList<>();

        for (int row = 0; row < grid.cells.length; ++row) {
            for (int col = 0; col < grid.cells[row].length; ++col) {
                int size = grid.findBestBrush(row, col);
                commands.addAll(grid.generateCommands(row, col, size));
                grid.clearArea(row, col, size);
            }
        }
        return commands;
    }

    public static void solveForFile(String inputPath, String outputPath) throws IOException {
        Grid grid = Grid.fromScanner(new Scanner(new File(inputPath)));
        writeCommands(outputPath, generateCommands_dumest(grid));
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
