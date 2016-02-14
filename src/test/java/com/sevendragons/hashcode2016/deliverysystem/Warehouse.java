package com.sevendragons.hashcode2016.deliverysystem;

public class Warehouse implements MapItem {

    final int id;
    final int row;
    final int col;
    final Pack pack;

    public Warehouse(int id, int row, int col, Pack pack) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.pack = pack;
    }

    public Pack buildPartialPack(Order order, Drone drone) {
        Pack partialPack = new Pack(pack.productCounts.length);
        for (int productId = 0; productId < pack.productCounts.length; ++productId) {
            if (order.pack.productCounts[productId] > 0 && pack.productCounts[productId] > 0) {
                // TODO should take into account Drone payload
                int requiredCount = order.pack.productCounts[productId] - drone.pack.productCounts[productId];
                int availableCount = pack.productCounts[productId];
                int count = Math.min(requiredCount, availableCount);
                partialPack.addProduct(productId, count);
            }
        }
        return partialPack;
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

        Warehouse warehouse = (Warehouse) o;

        if (id != warehouse.id) {
            return false;
        }
        if (row != warehouse.row) {
            return false;
        }
        if (col != warehouse.col) {
            return false;
        }
        return pack.equals(warehouse.pack);

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
