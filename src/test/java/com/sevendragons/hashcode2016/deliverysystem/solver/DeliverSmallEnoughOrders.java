package com.sevendragons.hashcode2016.deliverysystem.solver;

import com.sevendragons.hashcode2016.deliverysystem.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1. Pick drone 0
 * 2. For each order that can be served in one go
 * 3. Find a warehouse that can supply for the order
 * 4. Deliver for order
 */
public class DeliverSmallEnoughOrders implements Solver {
    @Override
    public Output solve(Input input) {
        Drone drone = input.drones.get(0);

        List<Command> commands = new ArrayList<>();
        for (Order order : input.orders) {
            if (input.maxPayload < order.pack.getWeight()) {
                continue;
            }
            WarehouseAndPack warehouseAndPack = findWarehouseAndPack(drone, order, input.warehouses);
            if (warehouseAndPack == null) {
                continue;
            }
            Warehouse warehouse = warehouseAndPack.warehouse;
            Pack pack = warehouseAndPack.pack;

            List<Command> commands2 = Arrays.asList(
                    new LoadCommand(drone, warehouse, pack),
                    new DeliverCommand(drone, order, pack)
            );
            commands.addAll(commands2);

            warehouse.pack.sub(pack);
        }
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
            Pack pack = warehouse.buildPartialPack(order, drone);
            if (pack.getWeight() == order.pack.getWeight()) {
                return new WarehouseAndPack(warehouse, pack);
            }
        }
        return null;
    }
}
