package com.sevendragons.hashcode2016.deliverysystem;

public class Product {
    public final int id;
    public final int weight;

    public Product(int id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        if (id != product.id) {
            return false;
        }
        return weight == product.weight;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + weight;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s %s", id, weight);
    }
}
