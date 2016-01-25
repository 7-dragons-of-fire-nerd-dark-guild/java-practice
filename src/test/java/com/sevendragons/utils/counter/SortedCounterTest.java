package com.sevendragons.utils.counter;

import com.sevendragons.utils.counter.api.SortedCounter;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SortedCounterTest {
    @Test
    public void test_top() {
        SortedCounter<Integer> counter = Counters.sortedCounter();
        counter.addAll(Arrays.asList(1, 4, 9, 3, 4, 5, 4, 9));
        assertEquals(Integer.valueOf(4), counter.highest());
    }

    @Test
    public void test_topCount() {
        SortedCounter<Integer> counter = Counters.sortedCounter();
        counter.addAll(Arrays.asList(1, 4, 9, 3, 4, 5, 4, 9));
        assertEquals(3, counter.highestCount());
    }

    @Test
    public void test_bottom() {
        SortedCounter<Integer> counter = Counters.sortedCounter();
        counter.addAll(Arrays.asList(1, 4, 9, 3, 4, 5, 4, 9));
        assertEquals(Integer.valueOf(1), counter.lowest());
    }

    @Test
    public void test_bottomCount() {
        SortedCounter<Integer> counter = Counters.sortedCounter();
        counter.addAll(Arrays.asList(1, 4, 9, 3, 4, 5, 4, 9));
        assertEquals(1, counter.lowestCount());
    }

    @Test
    public void test_getMostFrequentLetter() {
        SortedCounter<Character> counter = Counters.sortedCounter();
        String text = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
                "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla " +
                "pariatur. Excepteur sint occaecat cupidatat non proident, sunt in " +
                "culpa qui officia deserunt mollit anim id est laborum.";

        for (char c : text.replace(" ", "").toCharArray()) {
            counter.add(c);
        }
        assertEquals(Character.valueOf('i'), counter.highest());
    }

    @Test
    public void test_get2ndFrequentLetter() {
        SortedCounter<Character> counter = Counters.sortedCounter();
        String text = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris " +
                "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
                "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla " +
                "pariatur. Excepteur sint occaecat cupidatat non proident, sunt in " +
                "culpa qui officia deserunt mollit anim id est laborum.";

        for (char c : text.toCharArray()) {
            counter.add(c);
        }
        assertEquals(Character.valueOf('i'), counter.nth(2));
        assertEquals(43, counter.nthCount(2));
    }

    @Test
    public void test_map_hi_hi_hello() {
        SortedCounter<String> counter = Counters.sortedCounter();
        counter.add("hi");
        counter.add("hi");
        counter.add("hello");
        assertEquals("[hello=1, hi=2]", counter.sortedEntryList().toString());
    }

    @Test
    public void test_map_hello_hi_hi() {
        SortedCounter<String> counter = Counters.sortedCounter();
        counter.add("hello");
        counter.add("hi");
        counter.add("hi");
        assertEquals("[hello=1, hi=2]", counter.sortedEntryList().toString());
    }

    @Test
    public void test_map_hello_hi_hi_hello_hello() {
        SortedCounter<String> counter = Counters.sortedCounter();
        counter.add("hello");
        counter.add("hi");
        counter.add("hi");
        counter.add("hello");
        counter.add("hello");
        assertEquals("[hi=2, hello=3]", counter.sortedEntryList().toString());
    }
}
