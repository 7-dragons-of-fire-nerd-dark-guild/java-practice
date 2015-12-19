package com.sevendragons.practice.leetcode.hard.Skyline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

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
        final List<PointAndBuilding> leftRightPoints = getLeftRightPoints(buildings);
        return getSkyline(leftRightPoints);
    }

    private List<int[]> getSkyline(List<PointAndBuilding> leftRightPoints) {
        final PriorityQueue<Building> visibleBuildings = new PriorityQueue<>(Collections.reverseOrder(new Comparator<Building>() {
            @Override
            public int compare(Building o1, Building o2) {
                return Integer.compare(o1.height, o2.height);
            }
        }));

        List<int[]> skyline = new ArrayList<>();

        for (PointAndBuilding pointAndBuilding : leftRightPoints) {
            if (pointAndBuilding.point == pointAndBuilding.building.left) {
                if (isHigher(pointAndBuilding.building, visibleBuildings)) {
                    if (!skyline.isEmpty()) {
                        if (skyline.get(skyline.size() - 1)[0] == pointAndBuilding.point) {
                            skyline.remove(skyline.size() - 1);
                        }
                    }
                    if (!skyline.isEmpty()) {
                        if (skyline.get(skyline.size() - 1)[1] != pointAndBuilding.building.height) {
                            skyline.add(new int[]{pointAndBuilding.point, pointAndBuilding.building.height});
                        }
                    } else {
                        skyline.add(new int[]{pointAndBuilding.point, pointAndBuilding.building.height});
                    }
                }
                visibleBuildings.add(pointAndBuilding.building);
            } else {
                visibleBuildings.remove(pointAndBuilding.building);
                if (isHigher(pointAndBuilding.building, visibleBuildings)) {
                    if (!skyline.isEmpty()) {
                        if (skyline.get(skyline.size() - 1)[0] == pointAndBuilding.point) {
                            skyline.remove(skyline.size() - 1);
                        }
                    }
                    skyline.add(new int[]{pointAndBuilding.point, getHighestOrZero(visibleBuildings)});
                }
            }
        }

        return skyline;
    }

    private int getHighestOrZero(PriorityQueue<Building> visibleBuildings) {
        if (visibleBuildings.isEmpty()) {
            return 0;
        }
        return visibleBuildings.peek().height;
    }

    private boolean isHigher(Building building, PriorityQueue<Building> visibleBuildings) {
        if (visibleBuildings.isEmpty()) {
            return true;
        }
        return building.height > visibleBuildings.peek().height;
    }


    private List<PointAndBuilding> getLeftRightPoints(int[][] buildings) {

        List<PointAndBuilding> points = new ArrayList<>();
        for (int[] buildingData : buildings) {
            final Building building = new Building(buildingData[0], buildingData[1], buildingData[2]);
            points.add(new PointAndBuilding(building.left, building));
            points.add(new PointAndBuilding(building.right, building));
        }
        Collections.sort(points, new Comparator<PointAndBuilding>() {
            @Override
            public int compare(PointAndBuilding o1, PointAndBuilding o2) {
                return Integer.compare(o1.point, o2.point);
            }
        });

        return points;
    }

    private static class PointAndBuilding {
        final int point;
        final Building building;

        private PointAndBuilding(int point, Building building) {
            this.point = point;
            this.building = building;
        }
    }

    private class Building {
        final int left;
        final int right;
        final int height;

        private Building(int left, int right, int height) {
            this.left = left;
            this.right = right;
            this.height = height;
        }
    }

}
