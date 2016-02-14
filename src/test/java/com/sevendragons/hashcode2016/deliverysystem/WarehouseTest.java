package com.sevendragons.hashcode2016.deliverysystem;

import org.junit.Test;

import static org.junit.Assert.*;

public class WarehouseTest {

    private final Product product0 = new Product(0, 1);
    private final Product product1 = new Product(1, 10);
    private final int maxPayload = new Pack().add(product0, 100).add(product1, 100).getWeight();

    @Test
    public void getPartialPack_should_return_empty_when_no_match() {
        Warehouse warehouse = new Warehouse(-1, -1, -1, new Pack().add(product0));
        Order order = new Order(-1, -1, -1, new Pack().add(product1));
        Drone drone = new Drone(-1, -1, -1, -1, new Pack());
        assertTrue(warehouse.buildPartialPack(order, drone).isEmpty());
    }

    @Test
    public void getPartialPack_should_return_complete_pack_when_available() {
        Warehouse warehouse = new Warehouse(-1, -1, -1, new Pack().add(product0, 5).add(product1, 5));
        Pack pack = new Pack().add(product0).add(product1, 2);
        Order order = new Order(-1, -1, -1, pack);
        Drone drone = new Drone(-1, -1, -1, maxPayload, new Pack());
        assertEquals(pack, warehouse.buildPartialPack(order, drone));
    }

    @Test
    public void getPartialPack_should_return_partial_pack_when_partly_available() {
        Warehouse warehouse = new Warehouse(-1, -1, -1, new Pack().add(product0, 3).add(product1, 3));
        Order order = new Order(-1, -1, -1, new Pack().add(product0).add(product1, 5));
        Drone drone = new Drone(-1, -1, -1, maxPayload, new Pack());
        assertEquals(new Pack().add(product0).add(product1, 3), warehouse.buildPartialPack(order, drone));
    }
}
