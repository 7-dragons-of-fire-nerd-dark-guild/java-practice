package com.sevendragons.hashcode2016.deliverysystem;

public abstract class Command {
    final Drone drone;
    final char tag;

    Command(Drone drone, char tag) {
        this.drone = drone;
        this.tag = tag;
    }
}
