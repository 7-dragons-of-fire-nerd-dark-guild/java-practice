package com.sevendragons.hashcode2016.deliverysystem;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PrimitiveSolver1Test {

    PrimitiveSolver1 solver = new PrimitiveSolver1();
    private Drone drone = new Drone(-1, -1, -1, -1, Pack.fromCounts(0, 0, 0));

    @Test
    public void findWarehouse_should_find_warehouse_when_warehouse_has_everything() {
        Pack pack = Pack.fromCounts(0, 1, 1);
        Order order = new Order(-1, -1, -1, pack);

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(0, -1, -1, Pack.EMPTY),
                new Warehouse(1, -1, -1, pack.copy())
        );

        PrimitiveSolver1.WarehouseAndPack warehouseAndPack = solver.findWarehouseAndPack(drone, order, warehouses);
        assertEquals(warehouses.get(1), warehouseAndPack.warehouse);
        assertEquals(pack, warehouseAndPack.pack);
    }

    @Test
    public void findWarehouse_should_find_warehouse_when_warehouse_has_some() {
        Order order = new Order(-1, -1, -1, Pack.fromCounts(0, 1, 1));

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(0, -1, -1, Pack.EMPTY),
                new Warehouse(1, -1, -1, Pack.fromCounts(1, 1, 0))
        );

        PrimitiveSolver1.WarehouseAndPack warehouseAndPack = solver.findWarehouseAndPack(drone, order, warehouses);
        assertEquals(warehouses.get(1), warehouseAndPack.warehouse);
        assertEquals(Pack.fromCounts(0, 1, 0), warehouseAndPack.pack);
    }

    @Test
    public void findWarehouse_should_find_none_when_warehouse_has_none() {
        Order order = new Order(-1, -1, -1, Pack.fromCounts(0, 1, 1));

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(0, -1, -1, Pack.EMPTY),
                new Warehouse(1, -1, -1, Pack.fromCounts(1, 0, 0))
        );

        PrimitiveSolver1.WarehouseAndPack warehouseAndPack = solver.findWarehouseAndPack(drone, order, warehouses);
        assertEquals(null, warehouseAndPack);
    }
}
