package com.sevendragons.hashcode2016.facade;

public class Facade {
    public interface Command {

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
    }
}
