package com.sevendragons.hashcode2016.deliverysystem;

public abstract class LoadDeliverCommand extends Command {
    final Warehouse warehouse;
    final Pack pack;

    LoadDeliverCommand(Drone drone, char tag, Warehouse warehouse, Pack pack) {
        super(drone, tag);
        this.warehouse = warehouse;
        this.pack = pack;
    }
}
