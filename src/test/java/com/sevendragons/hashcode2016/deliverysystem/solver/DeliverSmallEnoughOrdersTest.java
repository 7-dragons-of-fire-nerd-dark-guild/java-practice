package com.sevendragons.hashcode2016.deliverysystem.solver;

import com.sevendragons.hashcode2016.deliverysystem.Input;
import com.sevendragons.hashcode2016.deliverysystem.Output;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;

public class DeliverSmallEnoughOrdersTest {

    private final Solver solver = new DeliverSmallEnoughOrders();

    @Ignore
    @Test
    public void test_mother_of_all_warehouses() throws FileNotFoundException {
        Input input = Input.fromPath("src/main/resources/delivery-system/mother_of_all_warehouses.in");
        Output output = solver.solve(input);
        System.out.println(output.toOutputLines());
    }
}

