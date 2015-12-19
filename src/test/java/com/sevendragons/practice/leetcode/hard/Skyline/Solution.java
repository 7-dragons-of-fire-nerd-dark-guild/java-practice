package com.sevendragons.practice.leetcode.hard.Skyline;

import java.util.Arrays;
import java.util.List;

public class Solution {
    // input: [ [L_i, R_i, H], ... ] : [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ]
    //  sorted ascending by L
    //
    // output: [ [x_i, y_i], ... ] : [ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ]
    //  sorted ascending by x
    //
    // "key point" : left endpoint of a horizontal line segment
    //      the last endpoint always has 0 height
    //
    // number of buildings: [0, 10000]
    //

    public List<int[]> getSkyline(int[][] buildings) {
        return Arrays.asList(new int[0]);
    }
}
