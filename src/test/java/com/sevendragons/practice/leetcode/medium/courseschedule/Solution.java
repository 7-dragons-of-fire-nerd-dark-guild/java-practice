package com.sevendragons.practice.leetcode.medium.coursesschedule;

import java.util.*;

public class Solution {

    //numCourses : id of course (ex: 3)
    //prerequesite: depencencies of course (ex: [[3,2],[2,1],[1,2], [3,1]])
    public boolean canFinish(int numberOfCourse, int[][] prerequisites) {

        // Init graph
        Map<Integer, Set<Integer>> prerequisitePerCourse = new HashMap<>();
        for (int[] pair : prerequisites) {
            if (prerequisitePerCourse.get(pair[0]) == null) {
                prerequisitePerCourse.put(pair[0], new HashSet<Integer>());
            }
            prerequisitePerCourse.get(pair[0]).add(pair[1]);
        }

        // Detect cycle
        return !isCyclic(numberOfCourse, prerequisitePerCourse);
    }

    private static boolean isCyclic(int numberOfCourse, Map<Integer, Set<Integer>> prerequisitePerCourse) {

        for (int course = 0; course < numberOfCourse; course++) {
            Set<Integer> targetCourses = new HashSet<Integer>();
            targetCourses.add(course);
            if (isCourseContainedInPrerequisites(prerequisitePerCourse, targetCourses, course) == true) {
                return true;
            }
        }

        return false;
    }

    private static boolean isCourseContainedInPrerequisites(Map<Integer, Set<Integer>> prerequisitePerCourse, Set<Integer> targetCourses, int prerequisiteToCheck) {

        for (int course : targetCourses) {
            if (prerequisitePerCourse.get(prerequisiteToCheck) != null && prerequisitePerCourse.get(prerequisiteToCheck).contains(course)) {
                return true;
            }
        }

        Set<Integer> prerequisites = prerequisitePerCourse.get(prerequisiteToCheck);
	if (prerequisites != null){
	    for (Integer prerequisite : prerequisitePerCourse.get(prerequisiteToCheck)) {
	        if (prerequisite != null){
	            targetCourses.add(prerequisite);
	            if (isCourseContainedInPrerequisites(prerequisitePerCourse, targetCourses, prerequisite)){
		        return true;
	            }
		}
	    }
	}
        

        return false;
    }

}
