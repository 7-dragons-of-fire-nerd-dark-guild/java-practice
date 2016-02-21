package com.sevendragons.hashcode2016.deliverysystem;

import java.util.List;

public abstract class Command {
    final Drone drone;
    final char tag;

    Command(Drone drone, char tag) {
        this.drone = drone;
        this.tag = tag;
    }

    public abstract List<String> generateOutputLines();
}
