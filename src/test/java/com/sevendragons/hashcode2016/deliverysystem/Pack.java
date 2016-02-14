package com.sevendragons.hashcode2016.deliverysystem;

import java.util.*;

public class Pack {

    private final Map<Product, Integer> items;

    private static final Comparator<Product> PRODUCT_COMPARATOR =
            (Comparator<Product>) (o1, o2) -> Integer.compare(o1.id, o2.id);

    private static final Comparator<Item> ITEM_COMPARATOR =
            (Comparator<Item>) (o1, o2) -> Integer.compare(o1.product.id, o2.product.id);

    public Pack() {
        this.items = new TreeMap<>(PRODUCT_COMPARATOR);
    }

    private Pack(Map<Product, Integer> items) {
        this();
        this.items.putAll(items);
    }

    public Pack copy() {
        return new Pack(items);
    }

    public Pack add(Product... products) {
        for (Product product : products) {
            add(product, 1);
        }
        return this;
    }

    public Pack add(Product product, int count) {
        items.put(product, items.getOrDefault(product, 0) + count);
        return this;
    }

    public int getCount(Product product) {
        return items.getOrDefault(product, 0);
    }

    private void setCount(Product product, int count) {
        items.put(product, count);
    }

    public Pack sub(Pack pack) {
        for (Item item : pack.getItems()) {
            setCount(item.product, Math.max(0, getCount(item.product) - item.getCount()));
        }
        return this;
    }

    // TODO keep running count instead of recalculating every time
    public int getWeight() {
        int weight = 0;
        for (Item item : getItems()) {
            weight += item.product.weight * item.getCount();
        }
        return weight;
    }

    public class Item {
        public final Product product;

        public Item(Product product) {
            this.product = product;
        }

        public int getCount() {
            return items.getOrDefault(product, 0);
        }
    }

    public Set<Item> getItems() {
        Set<Item> set = new TreeSet<>(ITEM_COMPARATOR);
        for (Product product : items.keySet()) {
            set.add(new Item(product));
        }
        return set;
    }

    public boolean isEmpty() {
        for (Integer count : items.values()) {
            if (count > 0) {
                return false;
            }
        }
        return true;
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

        return items.equals(pack.items);

    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
