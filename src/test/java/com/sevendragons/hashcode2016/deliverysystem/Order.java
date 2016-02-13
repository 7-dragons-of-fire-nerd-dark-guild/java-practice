package com.sevendragons.hashcode2016.deliverysystem;

import java.util.ArrayList;
import java.util.List;

public class Order extends AbstractMapItem {
    Pack pack;
    boolean completed;

    Order(int id, int row, int col) {
        super(id, row, col);
    }

    List<Warehouse> findWarehouses() {

        List<Warehouse> warehouses = new ArrayList<>();

        for (Warehouse warehouse : DeliverySystem.warehouseMap.values()) {
            boolean satisfying = true;
            for (Product product : pack.products.keySet()) {
                if (warehouse.getQuantity(product) < pack.products.get(product)) {
                    satisfying = false;
                }
            }
            if (satisfying) {
                warehouses.add(warehouse);
            }
        }

        return warehouses;
    }
}
