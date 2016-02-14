package com.sevendragons.hashcode2016.deliverysystem.solver;

import com.sevendragons.hashcode2016.deliverysystem.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DeliverFirstOrderTest {

    private final Product product0 = new Product(0, 1);
    private final Product product1 = new Product(1, 10);
    private final Product product2 = new Product(2, 20);
    private final int maxPayload = new Pack().add(product0, product1, product2).getWeight();

    private final DeliverFirstOrder solver = new DeliverFirstOrder();
    private final Drone drone = new Drone(-1, -1, -1, maxPayload, new Pack());

    @Test
    public void findWarehouse_should_find_warehouse_when_warehouse_has_everything() {
        Pack pack = new Pack().add(product1, product2);
        Order order = new Order(-1, -1, -1, pack);

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(0, -1, -1, new Pack()),
                new Warehouse(1, -1, -1, pack.copy())
        );

        DeliverFirstOrder.WarehouseAndPack warehouseAndPack = solver.findWarehouseAndPack(drone, order, warehouses);
        assertEquals(warehouses.get(1), warehouseAndPack.warehouse);
        assertEquals(pack, warehouseAndPack.pack);
    }

    @Test
    public void findWarehouse_should_find_warehouse_when_warehouse_has_some() {
        Pack pack = new Pack().add(product1, product2);
        Order order = new Order(-1, -1, -1, pack);

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(0, -1, -1, new Pack()),
                new Warehouse(1, -1, -1, new Pack().add(product0, product1))
        );

        DeliverFirstOrder.WarehouseAndPack warehouseAndPack = solver.findWarehouseAndPack(drone, order, warehouses);
        assertEquals(warehouses.get(1), warehouseAndPack.warehouse);
        assertEquals(new Pack().add(product1).add(product2, 0), warehouseAndPack.pack);
    }

    @Test
    public void findWarehouse_should_find_none_when_warehouse_has_none() {
        Order order = new Order(-1, -1, -1, new Pack().add(product1, product2));

        List<Warehouse> warehouses = Arrays.asList(
                new Warehouse(0, -1, -1, new Pack()),
                new Warehouse(1, -1, -1, new Pack().add(product0))
        );

        DeliverFirstOrder.WarehouseAndPack warehouseAndPack = solver.findWarehouseAndPack(drone, order, warehouses);
        assertEquals(null, warehouseAndPack);
    }

    @Ignore
    @Test
    public void test_mother_of_all_warehouses() throws FileNotFoundException {
        Input input = Input.fromPath("src/main/resources/delivery-system/mother_of_all_warehouses.in");
        Output output = solver.solve(input);
        System.out.println(output.toOutputLines());
    }
}

