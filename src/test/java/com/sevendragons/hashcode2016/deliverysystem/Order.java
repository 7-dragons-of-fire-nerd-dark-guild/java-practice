package com.sevendragons.hashcode2016.deliverysystem;

public class Order extends AbstractMapItem {

    public final Pack pack;

    public Order(int id, int row, int col, Pack pack) {
        super(id, row, col);
        this.pack = pack;
    }
}
