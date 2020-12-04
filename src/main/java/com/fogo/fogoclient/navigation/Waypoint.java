package com.fogo.fogoclient.navigation;

public class Waypoint {
    public String name;
    public String dimension;
    public int x, y, z;

    public Waypoint(String name, String dimension, int x, int y, int z) {
        this.name = name;
        this.dimension = dimension;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
