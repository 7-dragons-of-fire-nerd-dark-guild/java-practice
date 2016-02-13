package com.sevendragons.hashcode2016.deliverysystem;

import org.junit.Test;

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

        assertEquals(2, input.warehouseMap.size());
        assertEquals(5, input.warehouseMap.get(1).row);
        assertEquals(5, input.warehouseMap.get(1).col);

        assertEquals(3, input.orderMap.size());
        assertEquals(5, input.orderMap.get(2).row);
        assertEquals(6, input.orderMap.get(2).col);
        // NPE
        //        assertEquals(1, input.orderMap.get(2).products.products.size());
    }

}
