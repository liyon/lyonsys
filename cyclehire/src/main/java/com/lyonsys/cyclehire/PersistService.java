package com.lyonsys.cyclehire;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * Created by yong on 15/06/2016.
 */
public class PersistService implements Runnable {

    private BlockingQueue<Journey> journeys = new ArrayBlockingQueue<>(100);

    private FileWriter fileWriter ;

    private volatile boolean stop;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void persist(Journey journey)
    {
        if (!stop) {
            this.journeys.add(journey);
        }

    }

    public void start()
    {
        File dataFile = new File("./data/journey.data");
        try {
            new File("./data").mkdirs();
            dataFile.createNewFile();
            fileWriter = new FileWriter(dataFile);
        }catch (IOException e)
        {
            throw new RuntimeException("Error creating journey data file", e);
        }

        executorService.submit(this);

    }

    public void run(){
        try {
            while(!stop ) {
                Journey journey = journeys.poll(5, TimeUnit.SECONDS);
                if (journey != null)
                {
                    fileWriter.write(journey.toString() + "\n");
                }
            }

        }catch (Exception e)
        {

        }

    }

    public void stop()
    {
        executorService.shutdown();
        this.stop = true;
    }

}
