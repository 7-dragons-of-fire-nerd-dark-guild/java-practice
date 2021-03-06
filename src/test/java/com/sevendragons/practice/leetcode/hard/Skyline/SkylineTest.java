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
    public void test_single_building_2_9_10() {
        assertListEquals(Arrays.asList(new int[]{2, 10}, new int[]{9, 0}), solve(new int[][]{{2, 9, 10}}));
    }

    @Test
    public void test_single_group_of_buildings_2_9_10__3_7_15__5_12_12() {
        assertListEquals(Arrays.asList(
                        new int[]{2, 10},
                        new int[]{3, 15},
                        new int[]{7, 12},
                        new int[]{12, 0}
                ),
                solve(new int[][]{{2, 9, 10}, {3, 7, 15}, {5, 12, 12}})
        );
    }

    @Test
    public void test_overlap_left_lower_first() {
        assertListEquals(Arrays.asList(
                        new int[]{0, 3},
                        new int[]{2, 2},
                        new int[]{3, 0}
                ),
                solve(new int[][]{{0, 3, 2}, {0, 2, 3}})
        );
    }

    @Test
    public void test_overlap_left_higher_first() {
        assertListEquals(Arrays.asList(
                        new int[]{0, 3},
                        new int[]{2, 2},
                        new int[]{3, 0}
                ),
                solve(new int[][]{{0, 2, 3}, {0, 3, 2}})
        );
    }

    @Test
    public void test_overlap_right_higher_first() {
        assertListEquals(Arrays.asList(
                        new int[]{0, 3},
                        new int[]{3, 0}
                ),
                solve(new int[][]{{0, 3, 3}, {1, 3, 2}})
        );
    }

    @Test
    public void test_same_height_touching() {
        assertListEquals(Arrays.asList(
                        new int[]{0, 3},
                        new int[]{5, 0}
                ),
                solve(new int[][]{{0, 2, 3}, {2, 5, 3}})
        );
    }
}
