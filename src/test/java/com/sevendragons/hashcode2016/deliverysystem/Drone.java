package com.sevendragons.hashcode2016.deliverysystem;

public class Drone extends AbstractMapItem {

    final int maxPayload;

    final Pack pack;

    public Drone(int id, int row, int col, int maxPayload, Pack pack) {
        super(id, row, col);
        this.maxPayload = maxPayload;
        this.pack = pack;
    }

    public int calculateCapacityFor(Product product) {
        return (maxPayload - pack.getWeight()) / product.weight;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", id, row, col, maxPayload, pack);
    }
}
