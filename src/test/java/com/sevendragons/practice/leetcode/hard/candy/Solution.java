package com.sevendragons.practice.leetcode.hard.candy;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Solution {

    public int candy(int[] ratings) {
        return sum(calculate(ratings));
    }

    public int[] calculate(int[] ratings) {
        int[] candies = createArrayOfOnes(ratings.length);
        calculateFromLeft(ratings, candies);
        calculateFromRight(ratings, candies);
        return candies;
    }

    public int[] createArrayOfOnes(int length) {
        int[] arr = new int[length];
        Arrays.fill(arr, 1);
        return arr;
    }

    public int[] calculateFromLeft(int[] ratings, int[] candies) {
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }
        return candies;
    }

    public int[] calculateFromRight(int[] ratings, int[] candies) {
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }
        return candies;
    }

    public int sum(int[] ints) {
        return IntStream.of(ints).sum();
    }
}
