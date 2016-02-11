package com.sevendragons.hashcode2016.qualification;

import com.sevendragons.hashcode2016.qualification.Qualification.AbstractMapItem;
import com.sevendragons.hashcode2016.qualification.pj.Order;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class QualificationTest_Pierre {
    // Tips:
    //
    // Run a single test method in IntelliJ:
    //  => move the cursor inside the method body and press Control Shift F10
    //
    // Run all test methods of a class in IntelliJ:
    //  => move the cursor inside the class body but outside any method and press Control Shift F10
    //
    // Re-run the last test, whether one method or all methods:
    //  => press Shift F10
    //
    // Debug instead of run:
    //  => Control Shift F9 for first run
    //  => Shift F9 for re-run
    //
    // Run all test methods of a class with Maven:
    //  => mvn clean test -Dtest=FacadeTest
    //

    @Test
    public void test_something_gives_something() {
        // just to have some static imports ready to use
        // delete when you have actual meaningful tests
        assertEquals(Arrays.asList(1, 2), new ArrayList<>(Arrays.asList(1, 2)));
        assertNotEquals(Collections.emptyList(), Collections.emptySet());
    }


    @Test
    public void testLoad() throws Exception {
        Qualification.Product product = new Qualification.Product(1,10);
        Qualification.Drone drone = new Qualification.Drone(1, 1, 1, 50);
        Map map = new HashMap<>();
        map.put(product,1);
        drone.products = new Qualification.Products(map);
        Qualification.Warehouse warehouse = new Qualification.Warehouse(1, 1, 1, new Qualification.Products(new HashMap<>(map)));
        drone.load(warehouse,new Qualification.Products(new HashMap<>(map)));
        Collection<Integer> values = drone.products.products.values();
        for (int  value : values){
            assertEquals(2,value);
        }

    }

    @Test
    public void testCalculateHowMuchCanLoad() throws Exception {
        Qualification.Product product = new Qualification.Product(1,10);
        Qualification.Drone drone = new Qualification.Drone(1, 1, 1, 50);
        assertEquals(5,drone.calculateHowMuchCanLoad(product));
        Map map = new HashMap<>();
        map.put(new Qualification.Product(1,10),1);
        drone.products = new Qualification.Products(map);
        assertEquals(4,drone.calculateHowMuchCanLoad(product));

    }

    @Test
    public void testUnload() throws Exception {

    }

    @Test
    public void testCurrentWeight() throws Exception {
        Qualification.Drone drone = new Qualification.Drone(1, 1, 1, 50);
        assertEquals(0,drone.currentWeight());
        Map map = new HashMap<>();
        map.put(new Qualification.Product(1,10),1);
        drone.products = new Qualification.Products(map);
        assertEquals(10,drone.currentWeight());
        map.put(new Qualification.Product(1,10),2);
        assertEquals(30,drone.currentWeight());


    }

    @Test
    public void testDeliver() throws Exception {
        Qualification.Drone drone = new Qualification.Drone(1, 1, 1, 50);
        Map map = new HashMap<>();
        map.put(new Qualification.Product(1,10),1);
        drone.products = new Qualification.Products(map);

        Qualification.Order order = new Qualification.Order(1,1,1);
        order.products = new Qualification.Products(map);
        drone.deliver(order);
        assertEquals(true,order.completed);
    }

    @Test
    public void testCheckCompleted() throws Exception {
        Map map = new HashMap<>();
        map.put(new Qualification.Product(1,0),0);
        Qualification.Order order = new Qualification.Order(1,1,1);
        order.products = new Qualification.Products(map);
        new Qualification.Drone(1, 1, 1, 50).checkCompleted(order);
        assertEquals(true,order.completed);
        map.put(new Qualification.Product(1,0),10);
        new Qualification.Drone(1, 1, 1, 50).checkCompleted(order);
        assertEquals(false,order.completed);

    }
}
