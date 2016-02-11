package com.sevendragons.utils.counter.api;

import java.util.Collection;
import java.util.Map;

/**
 * Build a map of counts of objects.
 *
 * @param <T> type of the counted objects
 */
public interface Counter<T> {

    /**
     * Add an item, incrementing its count.
     *
     * @param item The item to count
     */
    void add(T item);

    /**
     * Add a collection of items, incrementing their count.
     *
     * @param items The items to count
     */
    void addAll(Collection<T> items);

    /**
     * Return the count of specified item.
     * If the item was never added to the counter, return 0.
     *
     * @param item The item whose count to find
     * @return The count of the item
     */
    int get(T item);

    /**
     * Return the internal map of counts.
     *
     * @return the map of counts
     */
    Map<T, Integer> map();
}
