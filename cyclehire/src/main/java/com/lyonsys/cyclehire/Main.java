package com.lyonsys.cyclehire;

import com.lyonsys.cyclehire.client.CycleHireClient;
import com.lyonsys.cyclehire.client.User;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;

/**
 * Created by yong on 17/06/2016.
 */
public class Main {


    public static void main(String[] args) {

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
        System.out.println("nearest station = " + dockingStation);
        Journey journey = client.hireACycle(dockingStation, user);
        System.out.println("Start journey = " + journey);

        user.setCurrentLocation(new Location(51.514048, -0.088463));

        DockingStation destination = client.findNearestDockingStation(user);

        // set time for testing purpose
        journey.setStartTime(new Date(startTime.getTime() - 1000 * 3600 * 4));

        client.returnCycle(journey, destination);


        System.out.println("End journey = " + journey);

        System.out.println("User balance = " + user.getBalance());
        System.out.println("Total Revenue Genereated = " + service.getCycleHireRevenue());

        service.stop();

        executorService.shutdown();


    }


}
