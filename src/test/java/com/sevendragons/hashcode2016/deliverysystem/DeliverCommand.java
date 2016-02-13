package com.sevendragons.hashcode2016.deliverysystem;

public class DeliverCommand extends LoadDeliverCommand {
    DeliverCommand(Drone drone, Warehouse warehouse, Pack pack) {
        super(drone, 'D', warehouse, pack);
    }
}
