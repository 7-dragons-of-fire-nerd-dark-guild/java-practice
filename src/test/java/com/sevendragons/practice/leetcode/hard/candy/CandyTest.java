package com.sevendragons.practice.leetcode.hard.candy;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CandyTest {

    private final Solution solution = new Solution();

    @Test
    public void test_createArrayOfOnes_0() {
        assertArrayEquals(new int[0], solution.createArrayOfOnes(0));
    }

    @Test
    public void test_createArrayOfOnes_1() {
        assertArrayEquals(new int[]{1}, solution.createArrayOfOnes(1));
    }

    @Test
    public void test_createArrayOfOnes_2() {
        assertArrayEquals(new int[]{1, 1}, solution.createArrayOfOnes(2));
    }

    @Test
    public void test_calculateFromLeft_2_2() {
        int[] ratings = {2, 2};
        assertArrayEquals(new int[]{1, 1}, solution.calculateFromLeft(ratings, solution.createArrayOfOnes(
                ratings.length)));
    }

    @Test
    public void test_calculateFromLeft_1_2_3() {
        int[] ratings = {1, 2, 3};
        assertArrayEquals(new int[]{1, 2, 3}, solution.calculateFromLeft(ratings, solution.createArrayOfOnes(
                ratings.length)));
    }

    @Test
    public void test_calculateFromLeft_3_2_1() {
        int[] ratings = {3, 2, 1};
        assertArrayEquals(new int[]{1, 1, 1}, solution.calculateFromLeft(ratings, solution.createArrayOfOnes(
                ratings.length)));

    }

    @Test
    public void test_calculateFromLeft_4_3_3_4() {
        int[] ratings = {4, 3, 3, 4};
        assertArrayEquals(new int[]{1, 1, 1, 2},
                solution.calculateFromLeft(ratings, solution.createArrayOfOnes(ratings.length)));
    }

    @Test
    public void test_calculateFromRight_4_3_3_4() {
        int[] ratings = {4, 3, 3, 4};
        assertArrayEquals(new int[]{2, 1, 1, 1},
                solution.calculateFromRight(ratings, solution.createArrayOfOnes(ratings.length)));
    }

    @Test
    public void test_calculate_4_3_3_4() {
        int[] ratings = {4, 3, 3, 4};
        assertArrayEquals(new int[]{2, 1, 1, 2}, solution.calculate(ratings));
    }

    @Test
    public void test_calculate_3_4_6_6_5() {
        int[] ratings = {3, 4, 6, 6, 5};
        assertArrayEquals(new int[]{1, 2, 3, 2, 1}, solution.calculate(ratings));
    }

    @Test
    public void test_calculate_4_2_3_4_1() {
        int[] ratings = {4, 2, 3, 4, 1};
        assertArrayEquals(new int[]{2, 1, 2, 3, 1}, solution.calculate(ratings));
    }

    @Test
    public void test_sum_1_2_3() {
        int[] ratings = {1, 2, 3};
        assertEquals(6, solution.sum(ratings));
    }
}
