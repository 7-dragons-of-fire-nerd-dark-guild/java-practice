package com.sevendragons.hashcode2016.deliverysystem;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OutputTest {

    @Test
    public void test_toOutputLines_example_1() {
        Drone drone0 = new Drone(0, -1, -1, -1, null);
        Drone drone1 = new Drone(1, -1, -1, -1, null);
        Warehouse warehouse0 = new Warehouse(0, -1, -1, null);
        Warehouse warehouse1 = new Warehouse(1, -1, -1, null);
        Order order0 = new Order(0, -1, -1, null);
        Order order1 = new Order(1, -1, -1, null);
        Order order2 = new Order(2, -1, -1, null);

        List<Command> commands = Arrays.asList(
                new LoadCommand(drone0, warehouse0, Pack.fromCounts(1, 1)),
                new DeliverCommand(drone0, order0, Pack.fromCounts(1)),
                new LoadCommand(drone0, warehouse1, Pack.fromCounts(0, 0, 1)),
                new DeliverCommand(drone0, order0, Pack.fromCounts(0, 0, 1)),

                new LoadCommand(drone1, warehouse1, Pack.fromCounts(0, 0, 1)),
                new DeliverCommand(drone1, order2, Pack.fromCounts(0, 0, 1)),
                new LoadCommand(drone1, warehouse0, Pack.fromCounts(1)),
                new DeliverCommand(drone1, order1, Pack.fromCounts(1))
        );

        Output output = new Output(commands);

        List<String> lines = output.toOutputLines();
        assertEquals(Arrays.asList(
                "9\n",
                "0 L 0 0 1\n",
                "0 L 0 1 1\n",
                "0 D 0 0 1\n",
                "0 L 1 2 1\n",
                "0 D 0 2 1\n",
                "1 L 1 2 1\n",
                "1 D 2 2 1\n",
                "1 L 0 0 1\n",
                "1 D 1 0 1\n"
        ), lines);
    }
}
