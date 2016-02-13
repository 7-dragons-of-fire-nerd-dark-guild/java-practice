package com.sevendragons.hashcode2016.deliverysystem;

public class Order implements MapItem {

    final int id;
    final int row;
    final int col;
    final Pack pack;

    public Order(int id, int row, int col, Pack pack) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.pack = pack;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if (id != order.id) {
            return false;
        }
        if (row != order.row) {
            return false;
        }
        if (col != order.col) {
            return false;
        }
        return pack.equals(order.pack);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + row;
        result = 31 * result + col;
        result = 31 * result + pack.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", id, row, col, pack);
    }
}
