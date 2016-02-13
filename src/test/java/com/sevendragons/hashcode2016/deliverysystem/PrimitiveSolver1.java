package com.sevendragons.hashcode2016.deliverysystem;

import java.util.Arrays;
import java.util.List;

/**
 * 1. Pick drone 0 and order 0
 * 2. Find any warehouse that contains any deliverable product for order 0
 * 3. Load at the warehouse for the order
 * 4. Deliver for order
 */
public class PrimitiveSolver1 implements Solver {
    @Override
    public Output solve(Input input) {
        Drone drone = input.drones.get(0);
        Order order = input.orders.get(0);

        WarehouseAndPack warehouseAndPack = findWarehouseAndPack(drone, order, input.warehouses);
        Warehouse warehouse = warehouseAndPack.warehouse;
        Pack pack = warehouseAndPack.pack;

        List<Command> commands = Arrays.asList(
                new LoadCommand(drone, warehouse, pack),
                new DeliverCommand(drone, order, pack)
        );
        return new Output(commands);
    }

    static class WarehouseAndPack {
        final Warehouse warehouse;
        final Pack pack;

        WarehouseAndPack(Warehouse warehouse, Pack pack) {
            this.warehouse = warehouse;
            this.pack = pack;
        }
    }

    public WarehouseAndPack findWarehouseAndPack(Drone drone, Order order, List<Warehouse> warehouses) {
        for (Warehouse warehouse : warehouses) {
            Pack partialPack = warehouse.buildPartialPack(order, drone);
            if (!partialPack.isEmpty()) {
                return new WarehouseAndPack(warehouse, partialPack);
            }
        }
        return null;
    }
}
