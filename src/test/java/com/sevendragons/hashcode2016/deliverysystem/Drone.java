package com.sevendragons.hashcode2016.deliverysystem;

public class Drone extends AbstractMapItem {

    final int maxPayload;

    final Pack pack;

    public Drone(int id, int row, int col, int maxPayload, Pack pack) {
        super(id, row, col);
        this.maxPayload = maxPayload;
        this.pack = pack;
    }
}
