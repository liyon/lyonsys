package com.lyonsys.cyclehire;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yong on 13/06/2016.
 */
public class CycleHireService {

    List<DockingStation> dockingStations = new ArrayList<DockingStation>();

    PersistService persister = new PersistService();

    PaymentService paymentService = new PaymentService();

    private AtomicBoolean started  = new AtomicBoolean(false);
    private Properties properties = new Properties();
    private static final String CONFIG_FILE = "dockingStations.properties";
        private Map<String, Double> ledger;

    private CountDownLatch startLatch = new CountDownLatch(1);
    private CountDownLatch stopLatch = new CountDownLatch(1);

    public void start(){

        loadConfig();

        initDockingStations();

        persister.start();

        System.out.println("System is up and running. Ready to provide service");

        startLatch.countDown();

        try {
            stopLatch.await();

            System.out.println("Existing");
            persister.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void initDockingStations() {
        String[] stations = properties.getProperty("dockingStations").split(",");

        for (String station: stations)
        {

            double latitude  = Double.parseDouble(properties.getProperty(station + ".location.latitude"));
            double longitude  = Double.parseDouble(properties.getProperty(station + ".location.longitude"));
            int numberOfSpaces = Integer.parseInt(properties.getProperty(station + ".numberOfSpaces"));
            DockingStation dockingStation = new DockingStation(station, new Location(latitude, longitude), numberOfSpaces);

            dockingStations.add(dockingStation);
        }
    }

    private void loadConfig() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
        if (inputStream != null)
        {
            try {
                properties.load(inputStream);
            }catch (IOException e) {
                throw new RuntimeException("Error reading property file", e);
            }
        } else{
            throw new RuntimeException("Property file dockingStations.properties not found");
        }
    }


    public DockingStation findNearestDockingStation(final Location location){
       return dockingStations.stream().min(Comparator.comparing(p->p.getDistanceTo(location))).get();

    }

    public void makePayment(Journey journey)
    {
        this.paymentService.makePayment(journey);
    }
    public void persistJourney(Journey journey)
    {
       this.persister.persist(journey);

    }

    public double getCycleHireRevenue () {
        return paymentService.getCycleHireRevenue();
    }

    public void waitForStartUp()
    {
        try {
            this.startLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void stop() {
        stopLatch.countDown();
    }
}
