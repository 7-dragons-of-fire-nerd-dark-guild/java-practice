package com.sevendragons.practice.leetcode.medium.courseschedule;

import com.sevendragons.practice.leetcode.medium.coursesschedule.Solution;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoursesScheduleTest {

    private final Solution solution = new Solution();

    @Test
    public void test_empty() {
        int[][] values = {};
        assertTrue(solution.canFinish(1, values));
    }

    @Test
    public void test_without_cycle() {
        int[][] values = {{0, 1}};
        assertTrue(solution.canFinish(1, values));
    }

    @Test
    public void test_with_cycle() {
        int[][] values = {{0, 1}, {1, 0}};
        assertTrue(solution.canFinish(1, values));
    }

}
