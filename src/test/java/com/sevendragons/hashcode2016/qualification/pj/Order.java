package com.sevendragons.hashcode2016.qualification.pj;

import java.util.Map;

/**
 * Created by animus on 11/02/16.
 */
public class Order {
    private int id;
    private int line;
    private int column;
    Map<Integer, Integer> productMap;

    public Order(int id, int line, int column, Map<Integer, Integer> productMap) {
        this.id = id;
        this.line = line;
        this.column = column;
        this.productMap = productMap;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public Map<Integer, Integer> getProductMap() {
        return productMap;
    }
}
