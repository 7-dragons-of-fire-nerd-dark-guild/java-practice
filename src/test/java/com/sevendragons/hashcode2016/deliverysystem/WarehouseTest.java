package com.sevendragons.hashcode2016.deliverysystem;

import org.junit.Test;

import static org.junit.Assert.*;

public class WarehouseTest {
    @Test
    public void getPartialPack_should_return_empty_when_no_match() {
        Warehouse warehouse = new Warehouse(-1, -1, -1, Pack.fromCounts(1, 0));
        Order order = new Order(-1, -1, -1, Pack.fromCounts(0, 1));
        Drone drone = new Drone(-1, -1, -1, -1, null);
        assertTrue(warehouse.buildPartialPack(order, drone).isEmpty());
    }

    @Test
    public void getPartialPack_should_return_complete_pack_when_available() {
        Warehouse warehouse = new Warehouse(-1, -1, -1, Pack.fromCounts(5, 5));
        Pack pack = Pack.fromCounts(1, 2);
        Order order = new Order(-1, -1, -1, pack);
        Drone drone = new Drone(-1, -1, -1, -1, Pack.fromCounts(0, 0));
        assertEquals(pack, warehouse.buildPartialPack(order, drone));
    }

    @Test
    public void getPartialPack_should_return_partial_pack_when_partly_available() {
        Warehouse warehouse = new Warehouse(-1, -1, -1, Pack.fromCounts(3, 3));
        Order order = new Order(-1, -1, -1, Pack.fromCounts(1, 5));
        Drone drone = new Drone(-1, -1, -1, -1, Pack.fromCounts(0, 0));
        assertEquals(Pack.fromCounts(1, 3), warehouse.buildPartialPack(order, drone));
    }
}
