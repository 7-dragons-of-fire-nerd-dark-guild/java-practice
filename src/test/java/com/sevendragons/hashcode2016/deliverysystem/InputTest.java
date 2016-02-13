package com.sevendragons.hashcode2016.deliverysystem;

import org.junit.Test;

import java.util.Arrays;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class InputTest {
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
        assertEquals(new Warehouse(0, 0, 0, Pack.fromCounts(5, 1, 0)), input.warehouses.get(0));
        assertEquals(new Warehouse(1, 5, 5, Pack.fromCounts(0, 10, 2)), input.warehouses.get(1));

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
                        new Order(0, 1, 1, Pack.fromCounts(1, 0, 1)),
                        new Order(1, 3, 3, Pack.fromCounts(3, 0, 0)),
                        new Order(2, 5, 6, Pack.fromCounts(0, 0, 1))
                ), input.orders
        );
    }

}
