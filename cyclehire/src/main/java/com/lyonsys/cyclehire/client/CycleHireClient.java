package com.lyonsys.cyclehire.client;

import com.lyonsys.cyclehire.*;

/**
 * Created by yong on 13/06/2016.
 */
public class CycleHireClient {

    private CycleHireService service;

    public CycleHireClient(CycleHireService service) {
        this.service = service;
    }

    public DockingStation findNearestDockingStation(User user)
    {
        return service.findNearestDockingStation(user.getCurrentLocation());
    }

    public Journey hireACycle(DockingStation dockingStation, User user)
    {
        Journey journey = null;
        for (DockingPoint dockingPoint: dockingStation.getDockingPoints())
        {
            Cycle cycle = dockingPoint.takeCycleIfAvailable();
            if (cycle != null)
            {
                journey = new Journey(user, cycle, dockingStation);
                break;
            }
        }
        return journey;
    }

    public void returnCycle(Journey journey, DockingStation dockingStation)
    {
        for (DockingPoint dockingPoint: dockingStation.getDockingPoints())
        {
           if (dockingPoint.parkCycleIfEmpty(journey.getCycle()))
           {
               journey.end(dockingStation);
               break;
           }
        }

        if (journey.isFinished())
        {
            service.makePayment(journey);
            service.persistJourney(journey);
        }

        // code to handle station full.
    }
}
