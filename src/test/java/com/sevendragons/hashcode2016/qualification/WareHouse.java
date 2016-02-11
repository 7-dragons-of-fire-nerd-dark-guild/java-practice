package com.sevendragons.hashcode2016.qualification;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by animus on 11/02/16.
 */
public class WareHouse {
    private int id;
    private Map<Integer, Integer> stockPerItemType = new HashMap<>();
    private int line;
    private int column;

    public WareHouse(int id, int line, int column, Map<Integer, Integer> stockPerItemType) {
        this.id = id;
        this.stockPerItemType = stockPerItemType;
        this.line = line;
        this.column = column;
    }

    public int getId() {
        return id;
    }

    public Map<Integer, Integer> getStockPerItemType() {
        return stockPerItemType;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
