package com.sevendragons.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class SieveOfEratosthenesTest {

    private boolean isPrime(int num) {
        Sieve sieve = new SieveOfEratosthenes(num);
        return sieve.isPrime(num);
    }

    @Test
    public void test_isPrime_0() {
        assertFalse(isPrime(0));
    }

    @Test
    public void test_isPrime_1() {
        assertFalse(isPrime(1));
    }

    @Test
    public void test_isPrime_2() {
        assertTrue(isPrime(2));
    }

    @Test
    public void test_isPrime_3() {
        assertTrue(isPrime(3));
    }

    @Test
    public void test_isPrime_4() {
        assertFalse(isPrime(4));
    }

    @Test
    public void test_isPrime_5() {
        assertTrue(isPrime(5));
    }

    @Test
    public void verify_first170_primes() {
        List<Integer> first170 = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67,
                71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179,
                181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293,
                307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431,
                433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569,
                571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691,
                701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839,
                853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991,
                997, 1009, 1013);

        Set<Integer> primes = new HashSet<>();
        primes.addAll(first170);

        int max = first170.get(first170.size() - 1);
        Sieve sieve = new SieveOfEratosthenes(max);

        for (int i = 0; i <= max; ++i) {
            assertEquals("checking " + i, primes.contains(i), sieve.isPrime(i));
        }
    }

    @Test(expected = IllegalStateException.class)
    public void test_isPrime_bigger_than_sieve() {
        int limit = 100;
        Sieve sieve = new SieveOfEratosthenes(limit);
        sieve.isPrime(limit + 1);
    }
}
