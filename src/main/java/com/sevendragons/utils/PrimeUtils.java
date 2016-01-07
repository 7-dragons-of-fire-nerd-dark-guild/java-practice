package com.sevendragons.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Various utilities related to prime numbers.
 *
 * See also:
 * https://codility.com/programmers/lessons/9/
 * https://codility.com/media/train/9-Sieve.pdf
 */
public class PrimeUtils {

    private PrimeUtils() {
        throw new AssertionError("utility class, forbidden constructor");
    }

    /**
     * Find the prime factors of an integer
     *
     * For example, the prime factors of 10: 2, 5
     *
     * Another example, the prime factors of 30: 2, 3, 5
     *
     * @param num the number
     * @return the prime factors
     */
    public static List<Integer> primeFactors(int num) {
        int[] minPrimeDivisors = findMinPrimeDivisors(num);
        return findFactors(num, minPrimeDivisors);
    }

    private static List<Integer> findFactors(int num, int[] minPrimeDivisors) {
        List<Integer> factors = new ArrayList<>();
        int x = num;
        for (; minPrimeDivisors[x] > 0; x /= minPrimeDivisors[x]) {
            factors.add(minPrimeDivisors[x]);
        }
        factors.add(x);
        return factors;
    }

    private static int[] findMinPrimeDivisors(int num) {
        int[] minPrimeDivisors = new int[num + 1];
        for (int divisor = 2; divisor * divisor <= num; ++divisor) {
            if (minPrimeDivisors[divisor] == 0) {
                for (int k = divisor * divisor; k <= num; k += divisor) {
                    if (minPrimeDivisors[k] == 0) {
                        minPrimeDivisors[k] = divisor;
                    }
                }
            }
        }
        return minPrimeDivisors;
    }
}
