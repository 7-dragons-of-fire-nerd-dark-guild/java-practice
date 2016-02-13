package com.sevendragons.hashcode2016.deliverysystem;

import java.util.Map;

public class Pack {
    final Map<Product, Integer> products;

    Pack(Map<Product, Integer> products) {
        this.products = products;
    }
}
