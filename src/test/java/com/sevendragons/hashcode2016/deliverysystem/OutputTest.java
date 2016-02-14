package com.sevendragons.hashcode2016.deliverysystem;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OutputTest {

    private final Product product0 = new Product(0, 1);
    private final Product product1 = new Product(1, 10);
    private final Product product2 = new Product(2, 20);

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
                new LoadCommand(drone0, warehouse0, new Pack().add(product0, product1)),
                new DeliverCommand(drone0, order0, new Pack().add(product0)),
                new LoadCommand(drone0, warehouse1, new Pack().add(product2)),
                new DeliverCommand(drone0, order0, new Pack().add(product2)),

                new LoadCommand(drone1, warehouse1, new Pack().add(product2)),
                new DeliverCommand(drone1, order2, new Pack().add(product2)),
                new LoadCommand(drone1, warehouse0, new Pack().add(product0)),
                new DeliverCommand(drone1, order1, new Pack().add(product0))
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
