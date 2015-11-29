package com.sevendragons.practice.leetcode.medium.coursesschedule;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Solution {

	public static int	NUM_COURSE;
	public Map<Integer,Set> accessibleNodes = new HashMap<Integer,Set>();

	public boolean canFinish(int numCourses, int[][] prerequisites) {
		NUM_COURSE = numCourses;
		
		// Init graph
		int[][] graph = new int[numCourses][numCourses];

		for (int i = 0; i < numCourses; i++) {
			for (int j = 0; j < numCourses; j++) {
				graph[i][j] = 0;
			}
		}

		for (int i = 0; i < prerequisites.length; i++) {
			for (int j = 0; j < prerequisites[i].length; j++) {
				graph[prerequisites[i][0]][prerequisites[i][1]] = 1;
			}
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

		toVisit.add(Integer.valueOf(node));
		

		while (toVisit.size() > 0) {
			Integer toVisitNode = toVisit.pop();
			visitedNode.add(toVisitNode);
			if (accessibleNodes.get(toVisitNode)!=null){
			    if (accessibleNodes.get(toVisitNode).contains(node)){
			        return true;
			    } else {
			        accessibleNodes.get(toVisitNode).add(node);
			    }
			}
			else{
			   for (int j = 0; j < NUM_COURSE; j++) {
				if (graph[toVisitNode.intValue()][j] == 1) {
					if (visitedNode.contains(Integer.valueOf(j))) {
						return true;
					}
					toVisit.add(Integer.valueOf(j));
				}
			    } 
			}
		}
		accessibleNodes.put(node,visitedNode);
		return false;
	}
}
