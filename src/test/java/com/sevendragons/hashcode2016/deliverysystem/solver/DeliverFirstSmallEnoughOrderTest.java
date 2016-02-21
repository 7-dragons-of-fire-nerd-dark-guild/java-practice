package com.sevendragons.hashcode2016.deliverysystem.solver;

import com.sevendragons.hashcode2016.deliverysystem.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeliverFirstSmallEnoughOrderTest {

    private final Solver solver = new DeliverFirstSmallEnoughOrder();

    @Ignore
    @Test
    public void test_mother_of_all_warehouses() throws FileNotFoundException {
        Input input = Input.fromPath("src/main/resources/delivery-system/mother_of_all_warehouses.in");
        Output output = solver.solve(input);
        System.out.println(output.toOutputLines());
    }
}

