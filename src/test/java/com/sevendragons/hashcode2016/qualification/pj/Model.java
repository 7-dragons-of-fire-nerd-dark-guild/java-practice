package com.sevendragons.hashcode2016.qualification.pj;

import java.util.Map;

/**
 * Created by animus on 11/02/16.
 */
public class Model {

    private int mapHeight;
    private int mapWidth;
    private int maxTurn;
    private int maxPayload;

    private Map<Integer, ProductType> productTypeMap;
    private Map<Integer, Drone> droneMap;
    private Map<Integer, WareHouse> warehouseMap;
    private Map<Integer, Order> orderMap;

    public Model(int mapHeight, int mapWidth, int maxTurn, int maxPayload, Map<Integer, ProductType> productTypeMap, Map<Integer, Drone> droneMap, Map<Integer, WareHouse> warehouseMap, Map<Integer, Order> orderMap) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.maxTurn = maxTurn;
        this.maxPayload = maxPayload;
        this.productTypeMap = productTypeMap;
        this.droneMap = droneMap;
        this.warehouseMap = warehouseMap;
        this.orderMap = orderMap;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMaxTurn() {
        return maxTurn;
    }

    public int getMaxPayload() {
        return maxPayload;
    }

    public Map<Integer, ProductType> getProductTypeMap() {
        return productTypeMap;
    }

    public Map<Integer, Drone> getDroneMap() {
        return droneMap;
    }

    public Map<Integer, WareHouse> getWarehouseMap() {
        return warehouseMap;
    }

    public Map<Integer, Order> getOrderMap() {
        return orderMap;
    }
}
