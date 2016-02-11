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

    private static class Building {
        private final int left;
        private final int right;
        private final int height;

        private Building(int left, int right, int height) {
            this.left = left;
            this.right = right;
            this.height = height;
        }
    }

    private static class PointAndBuilding {
        private final int point;
        private final Building building;

        private PointAndBuilding(int point, Building building) {
            this.point = point;
            this.building = building;
        }
    }

    public List<int[]> getSkyline(int[][] buildings) {
        final List<PointAndBuilding> leftRightPoints = getLeftRightPoints(buildings);
        return getSkyline(leftRightPoints);
    }

    private List<int[]> getSkyline(List<PointAndBuilding> leftRightPoints) {
        final PriorityQueue<Building> visibleBuildings = createMaxHeapByHeight();
        return getSkyline(leftRightPoints, visibleBuildings);
    }

    private List<int[]> getSkyline(List<PointAndBuilding> leftRightPoints, PriorityQueue<Building> visibleBuildings) {
        List<int[]> skyline = new ArrayList<>();

        for (PointAndBuilding pointAndBuilding : leftRightPoints) {
            checkAndAppend(skyline, visibleBuildings, pointAndBuilding);
        }

        return skyline;
    }

    private PriorityQueue<Building> createMaxHeapByHeight() {
        return new PriorityQueue<>(new Comparator<Building>() {
            @Override
            public int compare(Building o1, Building o2) {
                //noinspection SuspiciousNameCombination
                return -Integer.compare(o1.height, o2.height);
            }
        });
    }

    private void checkAndAppend(List<int[]> skyline, PriorityQueue<Building> visibleBuildings,
                                PointAndBuilding pointAndBuilding) {
        if (pointAndBuilding.point == pointAndBuilding.building.left) {
            addKeyPointIfAddedBuildingIsHigher(skyline, visibleBuildings, pointAndBuilding);
            visibleBuildings.add(pointAndBuilding.building);
        } else {
            visibleBuildings.remove(pointAndBuilding.building);
            addKeyPointIfRemovedBuildingWasHighest(skyline, visibleBuildings, pointAndBuilding);
        }
    }

    private void addKeyPointIfRemovedBuildingWasHighest(List<int[]> skyline, PriorityQueue<Building> visibleBuildings,
                                                        PointAndBuilding pointAndBuilding) {
        if (isHigher(pointAndBuilding.building, visibleBuildings)) {
            removeLastIfSameX(skyline, pointAndBuilding);
            addKeyPoint(skyline, pointAndBuilding.point, getHighestOrZero(visibleBuildings));
        }
    }

    private void addKeyPointIfAddedBuildingIsHigher(List<int[]> skyline, PriorityQueue<Building> visibleBuildings,
                                                    PointAndBuilding pointAndBuilding) {
        if (isHigher(pointAndBuilding.building, visibleBuildings)) {
            removeLastIfSameX(skyline, pointAndBuilding);
            addKeyPointIfDifferentHeight(skyline, pointAndBuilding);
        }
    }

    private void addKeyPointIfDifferentHeight(List<int[]> skyline, PointAndBuilding pointAndBuilding) {
        if (skyline.isEmpty() || getLastKeyPoint(skyline)[1] != pointAndBuilding.building.height) {
            addKeyPoint(skyline, pointAndBuilding.point, pointAndBuilding.building.height);
        }
    }

    private void removeLastIfSameX(List<int[]> skyline, PointAndBuilding pointAndBuilding) {
        if (!skyline.isEmpty() && getLastKeyPoint(skyline)[0] == pointAndBuilding.point) {
            skyline.remove(skyline.size() - 1);
        }
    }

    private int[] getLastKeyPoint(List<int[]> skyline) {
        return skyline.get(skyline.size() - 1);
    }

    private void addKeyPoint(List<int[]> skyline, int point, int height) {
        skyline.add(new int[]{point, height});
    }

    private int getHighestOrZero(PriorityQueue<Building> visibleBuildings) {
        if (visibleBuildings.isEmpty()) {
            return 0;
        }
        return visibleBuildings.peek().height;
    }

    private boolean isHigher(Building building, PriorityQueue<Building> visibleBuildings) {
        return visibleBuildings.isEmpty() || building.height > visibleBuildings.peek().height;
    }

    private List<PointAndBuilding> getLeftRightPoints(int[][] buildings) {
        List<PointAndBuilding> pointAndBuildingList = createPointAndBuildingList(buildings);
        sortByPoint(pointAndBuildingList);
        return pointAndBuildingList;
    }

    private void sortByPoint(List<PointAndBuilding> points) {
        Collections.sort(points, new Comparator<PointAndBuilding>() {
            @Override
            public int compare(PointAndBuilding o1, PointAndBuilding o2) {
                return Integer.compare(o1.point, o2.point);
            }
        });
    }

    private List<PointAndBuilding> createPointAndBuildingList(int[][] buildings) {
        List<PointAndBuilding> points = new ArrayList<>();

        for (int[] buildingData : buildings) {
            final Building building = new Building(buildingData[0], buildingData[1], buildingData[2]);
            points.add(new PointAndBuilding(building.left, building));
            points.add(new PointAndBuilding(building.right, building));
        }
        return points;
    }
}
