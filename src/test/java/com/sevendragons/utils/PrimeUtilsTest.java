package com.sevendragons.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.sevendragons.utils.PrimeUtils.primeFactors;
import static org.junit.Assert.*;

public class PrimeUtilsTest {
    @Test
    public void test_factors_of_10() {
        assertEquals(Arrays.asList(2, 5), primeFactors(10));
    }

    @Test
    public void test_factors_of_30() {
        assertEquals(Arrays.asList(2, 3, 5), primeFactors(30));
    }

    @Test
    public void test_factors_of_80() {
        assertEquals(Arrays.asList(2, 2, 2, 2, 5), primeFactors(80));
    }

    @Test
    public void test_factors_of_81() {
        assertEquals(Arrays.asList(3, 3, 3, 3), primeFactors(81));
    }

    @Test
    public void test_factors_of_83() {
        assertEquals(Collections.singletonList(83), primeFactors(83));
    }

    @Test
    public void test_factors_of_210() {
        assertEquals(Arrays.asList(2, 3, 5, 7), primeFactors(210));
    }
}
