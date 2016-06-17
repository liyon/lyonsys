package com.lyonsys.cyclehire;

import java.util.List;

/**
 * Created by yong on 13/06/2016.
 */
public class DockingStation {

    private final String name;
    private  final Location location;
    private final DockingPoint[] dockingPoints;


    public DockingStation(String name, Location location, int numberOfSpaces) {
        this.name = name;
        this.location = location;
        this.dockingPoints = new DockingPoint[numberOfSpaces];
        for (int i =0; i<numberOfSpaces; i++){
            dockingPoints[i]= new DockingPoint();
            //make the doccting station half full at start up.
            if (i<numberOfSpaces/2)
            {
                dockingPoints[i].parkCycleIfEmpty(new Cycle());
            }
        }
    }



    public DockingPoint[] getDockingPoints() {
        return dockingPoints;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public final double getDistanceTo(final Location location)
    {
        return Location.getDistanceBetween(this.location, location);
    }

    @Override
    public String toString() {
        return "DockingStation{" +
                "name='" + name + '\'' +
                '}';
    }
}
