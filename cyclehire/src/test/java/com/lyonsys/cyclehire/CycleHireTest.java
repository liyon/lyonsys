package com.lyonsys.cyclehire;

import com.lyonsys.cyclehire.client.CycleHireClient;
import com.lyonsys.cyclehire.client.User;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yong on 15/06/2016.
 */
public class CycleHireTest extends TestCase{



    @Test
    public void testHireACycle()
    {
        CycleHireService service = new CycleHireService();

        //Start service in a new thead.
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            public void run() {
                service.start();
            }
        });

        // wait for service to start up
        service.waitForStartUp();


        User user = new User("lyon", "Yong Li");
        user.setCurrentLocation(new Location(51.511795, -0.121483));
        user.addToBalance(100);

        Date startTime = new Date();

        CycleHireClient client = new CycleHireClient(service);

        DockingStation dockingStation = client.findNearestDockingStation(user);
        Journey journey = client.hireACycle(dockingStation, user);

        user.setCurrentLocation(new Location(51.514048, -0.088463));

        DockingStation destination = client.findNearestDockingStation(user);

        // set time for testing purpose
        journey.setStartTime(new Date(startTime.getTime() - 1000 * 3600 * 4));

        client.returnCycle(journey, destination);


        assertEquals(96, user.getBalance(), 0.0000001);
        assertEquals(4, service.getCycleHireRevenue(),0.000001);

        service.stop();

        executorService.shutdown();


    }

}
