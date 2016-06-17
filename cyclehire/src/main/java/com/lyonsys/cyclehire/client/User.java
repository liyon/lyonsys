package com.lyonsys.cyclehire.client;

import com.lyonsys.cyclehire.Location;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by yong on 13/06/2016.
 */
public class User {
    private String useId;
    private String name;
    private Location currentLocation;
    private AtomicReference<BigDecimal> balance;

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void addToBalance(double delta)
    {
        boolean done = false;
        while (!done){
            BigDecimal oldValue = this.balance.get();
            done = this.balance.compareAndSet(oldValue, oldValue.add(BigDecimal.valueOf(delta)));
        }
    }

    public double getBalance() {
        return balance.get().doubleValue();
    }

    public User(String useId, String name) {

        this.useId = useId;
        this.name = name;
        this.balance = new AtomicReference<>(BigDecimal.ZERO);
    }
}
