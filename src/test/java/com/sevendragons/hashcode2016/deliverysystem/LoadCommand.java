package com.sevendragons.hashcode2016.deliverysystem;

import java.util.ArrayList;
import java.util.List;

public class LoadCommand extends LoadDeliverCommand {
    private final Warehouse warehouse;

    public LoadCommand(Drone drone, Warehouse warehouse, Pack pack) {
        super(drone, 'L', pack);
        this.warehouse = warehouse;
    }

    @Override
    public List<String> generateOutputLines() {
        List<String> lines = new ArrayList<>();
        for (int productId = 0; productId < pack.productCounts.length; ++productId) {
            int count = pack.productCounts[productId];
            if (count > 0) {
                lines.add(String.format("%s %s %s %s %s\n", drone.id, tag, warehouse.id, productId, count));
            }
        }
        return lines;
    }
}
