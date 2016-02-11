package com.sevendragons.hashcode2016.qualification.pj;

/**
 * Created by animus on 11/02/16.
 */
public class Drone {
    int id;
    int row;
    int column;

    public Drone(int id, int row, int column) {
        this.id = id;
        this.row = row;
        this.column = column;
    }

    public int getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
