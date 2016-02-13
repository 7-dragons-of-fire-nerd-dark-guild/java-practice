package com.sevendragons.hashcode2016.deliverysystem;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Drone extends AbstractMapItem {
    final int maxWeight;

    Pack pack;
    List<Order> schedule;

    Drone(int id, int row, int col, int maxWeight) {
        super(id, row, col);
        this.maxWeight = maxWeight;
    }

    int calcDistanceToWarehouse(Warehouse warehouse) {
        return DeliverySystem.calculateDistance((MapItem)this,(MapItem)warehouse);

    }

    int calcDistanceToOrder(Order order) {
        return DeliverySystem.calculateDistance((MapItem)this,(MapItem)order);
    }

    void load(Warehouse warehouse, Pack pack) {
        Map<Product, Integer> products1 = pack.products;

        for(Map.Entry<Product,Integer> productIntegerEntry : products1.entrySet()){
            for(Map.Entry<Product, Integer> productIntegerEntryFromWarehouse : warehouse.pack.products.entrySet()){
                if(productIntegerEntry.getKey().id == productIntegerEntryFromWarehouse.getKey().id){
                    int number = calculateHowMuchCanLoad(productIntegerEntryFromWarehouse.getKey());
                    int loadnumber;
                    if(productIntegerEntryFromWarehouse.getValue()>number){
                        loadnumber = number;
                    }else {
                        loadnumber = productIntegerEntryFromWarehouse.getValue();
                    }
                    this.pack.products.put(productIntegerEntryFromWarehouse.getKey(),this.pack.products.get(productIntegerEntryFromWarehouse.getKey())+loadnumber);
                    warehouse.pack.products.replace(productIntegerEntryFromWarehouse.getKey(),productIntegerEntryFromWarehouse.getValue()-loadnumber);
                }
            }
        }
    }

    public int calculateHowMuchCanLoad(Product product) {
        int currentWeigth = currentWeight();
        return (maxWeight - currentWeigth) / product.weight;
    }


    public void deliver(Order order) {
        if (!order.completed) {
            for (Map.Entry<Product, Integer> entryFromProduct : pack.products.entrySet()) {
                for (Map.Entry<Product, Integer> entryFromOrder : order.pack.products.entrySet()) {
                    if (entryFromOrder.getKey().id == entryFromProduct.getKey().id) {
                        int valueToRemove = 0;
                        if(entryFromOrder.getValue()>entryFromProduct.getValue()){
                            valueToRemove = entryFromProduct.getValue()-entryFromProduct.getValue();
                        }else{
                            valueToRemove=entryFromProduct.getValue()-entryFromOrder.getValue();
                        }
                        pack.products.replace(entryFromProduct.getKey(),entryFromProduct.getValue(),valueToRemove);
                        order.pack.products.replace(entryFromOrder.getKey(),entryFromOrder.getValue(),valueToRemove);

                    }
                }
            }
        }
        checkCompleted(order);
    }

    public void checkCompleted(Order order) {
        Map<Product, Integer> products = order.pack.products;

        Collection<Integer> values = products.values();
        boolean isCompleted = true;
        for (int value: values){
            if (value>0){
                isCompleted = false;
            }
        }
        order.completed = isCompleted;

    }

    int currentWeight(){
        if(pack == null || pack.products == null || pack.products.isEmpty()){
            return 0;
        }
        Set<Map.Entry<Product, Integer>> entries = pack.products.entrySet();
        int weight = 0;
        for (Map.Entry<Product,Integer> entry : entries){
                weight +=entry.getKey().weight*entry.getValue();
        }
        return weight;
    }

    Pair<Order, Warehouse> findNextOrderWarehouse() {
        Order closestOrder = null;
        Warehouse closestWarehouse = null;

        int minDistance = Integer.MAX_VALUE;

        for (Order order : DeliverySystem.orderMap.values()) {
            int distanceToOrder = calcDistanceToOrder(order);

            for (Warehouse warehouse : order.findWarehouses()) {
                int distance = distanceToOrder + DeliverySystem.order2warehouse[order.id][warehouse.id];
                if (distance < minDistance) {
                    minDistance = distance;
                    closestOrder = order;
                    closestWarehouse = warehouse;
                }
            }
        }

        if (closestOrder == null) {
            return null;
        }

        return new Pair<>(closestOrder, closestWarehouse);
    }

}
