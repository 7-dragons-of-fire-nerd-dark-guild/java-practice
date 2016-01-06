package com.sevendragons.utils;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class PrimesTest {
    @Test
    public void test_sum_first_10() {
        assertEquals(129, Primes.intStream1k().limit(10).sum());
    }

    @Test
    public void test_iterate_first_10() {
        Iterator<Integer> iterator = Primes.iterator1k();
        assertEquals(2, iterator.next().intValue());
        assertEquals(3, iterator.next().intValue());
        assertEquals(5, iterator.next().intValue());
        assertEquals(7, iterator.next().intValue());
        assertEquals(11, iterator.next().intValue());
        assertEquals(13, iterator.next().intValue());
        assertEquals(17, iterator.next().intValue());
        assertEquals(19, iterator.next().intValue());
        assertEquals(23, iterator.next().intValue());
        assertEquals(29, iterator.next().intValue());
    }
}
