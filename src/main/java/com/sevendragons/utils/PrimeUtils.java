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
        int[] maxPrimeFactors = findMaxPrimeFactors(num);
        return findFactors(num, maxPrimeFactors);
    }

    private static List<Integer> findFactors(int num, int[] maxPrimeFactor) {
        List<Integer> factors = new ArrayList<>();
        int x = num;
        for (; maxPrimeFactor[x] > 0; x /= maxPrimeFactor[x]) {
            factors.add(maxPrimeFactor[x]);
        }
        factors.add(x);
        return factors;
    }

    private static int[] findMaxPrimeFactors(int num) {
        int[] maxPrimeFactors = new int[num + 1];
        for (int divisor = 2; divisor * divisor <= num; ++divisor) {
            if (maxPrimeFactors[divisor] == 0) {
                for (int k = divisor * divisor; k <= num; k += divisor) {
                    if (maxPrimeFactors[k] == 0) {
                        maxPrimeFactors[k] = divisor;
                    }
                }
            }
        }
        return maxPrimeFactors;
    }
}
