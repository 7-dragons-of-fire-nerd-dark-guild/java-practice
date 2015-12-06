package com.sevendragons.practice.leetcode.medium.coursesschedule;

import java.util.*;

public class Solution {

    public static int NUM_COURSE;


    //numCourses : id of course (ex: 3)
    //prerequesite: depencencies of course (ex: [[3,2],[2,1],[1,2], [3,1]])
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        NUM_COURSE = numCourses;

        // Init graph
        Map<Integer, Set<Integer>> prerequisitePerCourse = new HashMap<>();
        for (int[] pair : prerequisites) {
            if (prerequisitePerCourse.get(pair[0]) == null) {
                prerequisitePerCourse.put(pair[0], new HashSet<Integer>());
            }
            prerequisitePerCourse.get(pair[0]).add(pair[1]);
        }

        // Detect cycle
        return !isCyclic(prerequisitePerCourse);
    }

    private static boolean isCyclic(Map<Integer, Set<Integer>> prerequisitePerCourse) {

        for (int course : prerequisitePerCourse.keySet()) {
            if (isCourseContainedInPrerequisites(prerequisitePerCourse, course, new HashSet<Integer>()) == true) {
                return true;
            }
        }

        return false;
    }

    private static boolean isCourseContainedInPrerequisites(Map<Integer, Set<Integer>> prerequisitePerCourse, int course, Set<Integer> toCheck) {

        for (int i : toCheck) {
            if (prerequisitePerCourse.get(course) != null && prerequisitePerCourse.get(course).contains(i)) {
                return true;
            }
        }

        for (Integer prerequisite : prerequisitePerCourse.get(course)) {
            if (prerequisite == null) {
                break;
            }
            toCheck.add(prerequisite);
            isCourseContainedInPrerequisites(prerequisitePerCourse, prerequisite, toCheck);
        }

        return false;
    }

}
