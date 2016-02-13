package com.sevendragons.hashcode2016.deliverysystem;

import java.util.Arrays;

public class Pack {

    final int[] productCounts;

    public Pack(int productTypeCount) {
        this.productCounts = new int[productTypeCount];
    }

    private Pack(int[] productCounts) {
        this.productCounts = productCounts;
    }

    public void addProduct(int productId, int count) {
        productCounts[productId] += count;
    }

    public static Pack fromCounts(int... productCounts) {
        return new Pack(productCounts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pack pack = (Pack) o;

        return Arrays.equals(productCounts, pack.productCounts);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(productCounts);
    }

    @Override
    public String toString() {
        return Arrays.toString(productCounts);
    }
}
