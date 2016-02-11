package com.sevendragons.hashcode2016.qualification.pj;

/**
 * Created by animus on 11/02/16.
 */
public class Order {
    private int line;
    private int column;
    int[] numberOfItems;

    public Order(int line, int column, int[] numberOfItems) {
        this.line = line;
        this.column = column;
        this.numberOfItems = numberOfItems;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int[] getNumberOfItems() {
        return numberOfItems;
    }

    public int getNumberForItem(int itemType){
        return numberOfItems[itemType];
    }
}
