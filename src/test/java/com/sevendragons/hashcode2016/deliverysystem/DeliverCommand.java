package com.sevendragons.hashcode2016.deliverysystem;

import java.util.ArrayList;
import java.util.List;

public class DeliverCommand extends LoadDeliverCommand {
    private final Order order;

    public DeliverCommand(Drone drone, Order order, Pack pack) {
        super(drone, 'D', pack);
        this.order = order;
    }

    @Override
    public List<String> generateOutputLines() {
        List<String> lines = new ArrayList<>();
        for (Pack.Item item : pack.getItems()) {
            int productId = item.product.id;
            int count = item.getCount();
            if (count > 0) {
                lines.add(String.format("%s %s %s %s %s\n", drone.id, tag, order.id, productId, count));
            }
        }
        return lines;
    }
}
