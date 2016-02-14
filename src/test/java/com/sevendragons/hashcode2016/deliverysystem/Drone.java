package com.sevendragons.hashcode2016.deliverysystem;

public class Drone extends AbstractMapItem {

    final int maxPayload;

    final Pack pack;

    private int readyTime;

    public Drone(int id, int row, int col, int maxPayload, Pack pack) {
        super(id, row, col);
        this.maxPayload = maxPayload;
        this.pack = pack;
        readyTime = 0;
    }

    public int getReadyTime() {
        return readyTime;
    }

    public void addReadyTime(int readyTime) {
        this.readyTime += readyTime;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", id, row, col, maxPayload, pack);
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
