package com.sevendragons.practice.leetcode.easy.nimgame;

public class Solution {
    
    // 1 2 3 4 5 6 7 8 9 10 11 12 13....
    // G G G P G G G P G G  G  P
    
    public boolean canWinNim(int n) {
        return n%4!=0;
    }

}
