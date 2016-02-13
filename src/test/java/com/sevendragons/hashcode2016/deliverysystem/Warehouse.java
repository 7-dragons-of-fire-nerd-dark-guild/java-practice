package com.sevendragons.hashcode2016.deliverysystem;

public class Warehouse extends AbstractMapItem {
    final Pack pack;

    Warehouse(int id, int row, int col, Pack pack) {
        super(id, row, col);
        this.pack = pack;
    }

    // remove from warehouse
    void load(Product product, int quantity) {
        if (pack.products.containsKey(product)) {
            pack.products.put(product, pack.products.get(product) - quantity);
            if (pack.products.get(product) <= 0) {
                pack.products.remove(product);
            }
        }
    }

    int getQuantity (Product product) {
        return pack.products.get(product) != null ? pack.products.get(product) : 0;
    }
}
