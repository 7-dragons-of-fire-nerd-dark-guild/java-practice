package com.sevendragons.hashcode2016.deliverysystem;

import java.util.ArrayList;
import java.util.List;

public class DeliverCommand extends LoadDeliverCommand {
    private final Order order;

    DeliverCommand(Drone drone, Order order, Pack pack) {
        super(drone, 'D', pack);
        this.order = order;
    }

    @Override
    public List<String> generateOutputLines() {
        List<String> lines = new ArrayList<>();
        for (int productId = 0; productId < pack.productCounts.length; ++productId) {
            int count = pack.productCounts[productId];
            if (count > 0) {
                lines.add(String.format("%s %s %s %s %s\n", drone.id, tag, order.id, productId, count));
            }
        }
        return lines;
    }
}
