package com.sevendragons.utils.counter;

import com.sevendragons.utils.counter.api.Counter;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CounterTest {

    @Test
    public void test_add_one() {
        Counter<String> counter = Counters.counter();
        assertTrue(counter.map().isEmpty());

        counter.add("hello");
        assertFalse(counter.map().isEmpty());
    }

    @Test
    public void test_add_one_twice() {
        Counter<String> counter = Counters.counter();
        assertTrue(counter.map().isEmpty());

        counter.add("hello");
        assertEquals(1, counter.map().size());

        counter.add("hello");
        assertEquals(1, counter.map().size());
    }

    @Test
    public void testAddAll() {
        Counter<String> counter = Counters.counter();
        counter.addAll(Arrays.asList("hello", "hello", "hello", "world"));
        assertEquals(3, counter.get("hello"));
        assertEquals(1, counter.get("world"));
    }

    @Test
    public void testEntrySet() {
        Counter<String> counter = Counters.counter();
        counter.addAll(Arrays.asList("hello", "hello", "hello", "world"));

        Map<String, Integer> entries = new HashMap<>();
        entries.put("hello", 3);
        entries.put("world", 1);
        assertEquals(entries, counter.map());
    }

    @Test
    public void testGet() {
        Counter<String> counter = Counters.counter();
        assertEquals(0, counter.get("hello"));

        counter.add("hello");
        assertEquals(1, counter.get("hello"));

        counter.add("hello");
        assertEquals(2, counter.get("hello"));
    }
}
