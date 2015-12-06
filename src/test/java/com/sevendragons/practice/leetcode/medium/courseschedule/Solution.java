package com.sevendragons.practice.leetcode.medium.coursesschedule;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Solution {

	public static int NUM_COURSE;
	public static Map<Integer,Set> accessibleNodes = new HashMap<Integer,Set>();

	//numCourses : id of course (ex: 3)
	//prerequesite: depencencies of course (ex: [[3,2],[2,1],[2,1]]) 
	public boolean canFinish(int numCourses, int[][] prerequisites) {
		NUM_COURSE = numCourses;
		
		// Init graph
		int[][] graph = new int[numCourses][numCourses];
		for (int[] pair : prerequisites) {
			graph[pair[0]][pair[1]] = 1;
		}

		// Detect cycle
		for (int i = 0; i < numCourses; i++) {
			if (isCyclic(graph, i)) {
				return false;
			}
		}

		return true;
	}

	private static boolean isCyclic(int[][] graph, int node) {
		Set<Integer> visitedNode = new HashSet<Integer>();
		Stack<Integer> toVisit = new Stack<Integer>();

		toVisit.add(node);

		while (!toVisit.isEmpty()) {
			Integer toVisitNode = toVisit.pop();
			visitedNode.add(toVisitNode);
			if (accessibleNodes.get(toVisitNode) != null){
			    if (accessibleNodes.get(toVisitNode).contains(node)){
			        return true;
			    } else {
			        accessibleNodes.get(toVisitNode).add(node);
			    }
			}
			else{
			   for (int j = 0; j < NUM_COURSE; j++) {
				if (graph[toVisitNode][j] == 1) {
					if (visitedNode.contains(j)) {
						return true;
					}
					toVisit.add(j);
				}
			    } 
			}
		}
		accessibleNodes.put(node,visitedNode);
		return false;
	}
}
