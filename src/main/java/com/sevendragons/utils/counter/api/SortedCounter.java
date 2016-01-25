package com.sevendragons.utils.counter.api;

import java.util.List;
import java.util.Map;

/**
 * Build a map of counts of objects,
 * and provide easy access to entries sorted by their counts.
 *
 * @param <T> type of the counted objects
 */
public interface SortedCounter<T> extends Counter<T> {

    /**
     * Get the entry with the highest count, as a pair of item -> count
     *
     * @return the item -> count pair with the highest count
     */
    Map.Entry<T, Integer> highestEntry();

    /**
     * Convenience method to get the item with the highest count
     *
     * @return the item with the highest count
     */
    T highest();

    /**
     * Convenience method to get the count of the item with the highest count
     *
     * @return the count of the item
     */
    int highestCount();

    /**
     * Get the entry with the lowest count, as a pair of item -> count
     *
     * @return the item -> count pair with the lowest count
     */
    Map.Entry<T, Integer> lowestEntry();

    /**
     * Convenience method to get the item with the lowest count
     *
     * @return the item with the lowest count
     */
    T lowest();

    /**
     * Convenience method to get the count of the item with the lowest count
     *
     * @return the count of the item
     */
    int lowestCount();

    /**
     * Get the n-th entry by count, as a pair of item -> count.
     * The index n must be 0-based, and 0-th is the item with the highest count.
     *
     * @return the n-th item -> count pair
     */
    Map.Entry<T, Integer> nthEntry(int n);

    /**
     * Get the n-th item by count.
     * The index n must be 0-based, and 0-th is the item with the highest count.
     *
     * @return the n-th item
     */
    T nth(int n);

    /**
     * Get the count of n-th item.
     * The index n must be 0-based, and 0-th is the item with the highest count.
     *
     * @return the count of the n-th item
     */
    int nthCount(int n);

    /**
     * Return the list of item -> count pairs, sorted by count, high to low
     *
     * @return list of item -> count pairs
     */
    List<Map.Entry<T, Integer>> sortedEntryList();
}
