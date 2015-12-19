package com.sevendragons.practice.leetcode.hard.Skyline;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SkylineTest {

    private final Solution solution = new Solution();

    private List<int[]> solve(int[][] buildings) {
        return solution.getSkyline(buildings);
    }

    private void assertListEquals(List<int[]> expected, List<int[]> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); ++i) {
            assertArrayEquals(expected.get(i), actual.get(i));
        }
    }

    @Test
    public void test_2_9_10() {
        assertListEquals(Arrays.asList(new int[]{2, 10}, new int[]{9, 0}), solve(new int[][]{{2, 9, 10}}));
    }

    @Test
    public void test_2_9_10__3_7_15__5_12_12() {
        assertListEquals(
                Arrays.asList(
                        new int[]{2, 10},
                        new int[]{3, 15},
                        new int[]{7, 12},
                        new int[]{12, 0}
                ),
                solve(new int[][]{{2, 9, 10}, {3, 7, 15}, {5, 12, 12}})
        );
    }

    @Test
    public void test_singleton_3() {
        assertTrue(true);
    }

    @Test
    public void test_row_1_2_3() {
        assertNull(null);
    }

    @Test
    public void test_example_1() {
        // [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ]
        // -> [ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ]
        assertNull(null);
    }
}
