package com.sevendragons.hashcode2016.deliverysystem;

public abstract class LoadDeliverCommand extends Command {
    final Pack pack;

    LoadDeliverCommand(Drone drone, char tag, Pack pack) {
        super(drone, tag);
        this.pack = pack;
    }
}
