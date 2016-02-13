package com.sevendragons.hashcode2016.deliverysystem;

public class LoadCommand extends LoadDeliverCommand {
    LoadCommand(Drone drone, Warehouse warehouse, Pack pack) {
        super(drone, 'L', warehouse, pack);
    }
}
