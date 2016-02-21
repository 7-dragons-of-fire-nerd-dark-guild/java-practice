package com.sevendragons.hashcode2016.deliverysystem.solver;

import com.sevendragons.hashcode2016.deliverysystem.*;

import java.util.*;

import static com.sevendragons.hashcode2016.deliverysystem.DeliverySystem.calculateDistance;

/**
 * 1. Use a PriorityQueue to track available drones
 * 2. For each order that can be served in one go
 * 3. Find a warehouse that can supply for the order
 * 4. Deliver for order
 */
public class DeliverSmallEnoughOrdersWithManyDrones implements Solver {
    @Override
    public Output solve(Input input) {
        PriorityQueue<Drone> drones = new PriorityQueue<>((Comparator<Drone>) (o1, o2) -> Integer.compare(o1.getReadyTime(), o2.getReadyTime()));
        drones.addAll(input.drones);

        List<Command> commands = new ArrayList<>();
        for (Order order : input.orders) {
            if (input.maxPayload < order.pack.getWeight()) {
                continue;
            }

            Drone topDrone = drones.peek();

            WarehouseAndPack warehouseAndPack = findWarehouseAndPack(topDrone, order, input.warehouses);
            if (warehouseAndPack == null) {
                continue;
            }
            Warehouse warehouse = warehouseAndPack.warehouse;
            Pack pack = warehouseAndPack.pack;

            Drone drone = drones.poll();

            List<Command> commands2 = Arrays.asList(
                    new LoadCommand(drone, warehouse, pack),
                    new DeliverCommand(drone, order, pack)
            );
            commands.addAll(commands2);

            warehouse.pack.sub(pack);

            int timeToReady = 2 + calculateDistance(drone, warehouse) + calculateDistance(warehouse, order);

            drone.addReadyTime(timeToReady);
            drone.setPosition(order.getRow(), order.getCol());
            drones.add(drone);
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
