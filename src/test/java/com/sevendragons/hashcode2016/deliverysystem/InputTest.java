package com.sevendragons.hashcode2016.deliverysystem;

import org.junit.Test;

import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class InputTest {

    private final Product product0 = new Product(0, 100);
    private final Product product1 = new Product(1, 5);
    private final Product product2 = new Product(2, 450);

    @Test
    public void test_example_1() {
        String text = "" +
                "100 100 3 50 500\n" +
                "3\n" +
                "100 5 450\n" +
                "2\n" +
                "0 0\n" +
                "5 1 0\n" +
                "5 5\n" +
                "0 10 2\n" +
                "3\n" +
                "1 1\n" +
                "2\n" +
                "2 0\n" +
                "3 3\n" +
                "3\n" +
                "0 0 0\n" +
                "5 6\n" +
                "1\n" +
                "2\n";

        Input input = Input.fromScanner(new Scanner(text));

        assertEquals(3, input.drones.size());

        assertEquals(2, input.warehouses.size());

        Warehouse warehouse0 = new Warehouse(0, 0, 0, new Pack().add(product0, 5).add(product1).add(product2, 0));
        assertEquals(warehouse0, input.warehouses.get(0));

        Warehouse warehouse1 = new Warehouse(1, 5, 5, new Pack().add(product0, 0).add(product1, 10).add(product2, 2));
        assertEquals(warehouse1, input.warehouses.get(1));

        assertEquals(3, input.products.size());
        assertEquals(
                Arrays.asList(
                        new Product(0, 100),
                        new Product(1, 5),
                        new Product(2, 450)
                ), input.products
        );

        assertEquals(
                Arrays.asList(
                        new Order(0, 1, 1, new Pack().add(product0, product2)),
                        new Order(1, 3, 3, new Pack().add(product0, 3)),
                        new Order(2, 5, 6, new Pack().add(product2))
                ), input.orders
        );
    }

}
