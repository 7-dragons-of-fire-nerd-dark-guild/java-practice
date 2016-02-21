package com.sevendragons.hashcode2016.deliverysystem;

import org.junit.Test;

import static org.junit.Assert.*;

public class PackTest {
    @Test
    public void test_weight_of_empty_pack_is_0() {
        assertEquals(0, new Pack().getWeight());
    }

    @Test
    public void test_weight_of_1_item() {
        int weight = 3;
        Product product = new Product(-1, weight);
        assertEquals(weight, new Pack().add(product).getWeight());
    }

    @Test
    public void test_weight_of_2_same_items() {
        int weight = 3;
        int count = 2;
        Product product = new Product(-1, weight);
        assertEquals(weight * count, new Pack().add(product, count).getWeight());
    }

    @Test
    public void test_weight_of_2_different_items() {
        int weight1 = 3;
        int weight2 = 5;
        Product product1 = new Product(0, weight1);
        Product product2 = new Product(1, weight2);
        assertEquals(weight1 + weight2, new Pack().add(product1, product2).getWeight());
    }
}
