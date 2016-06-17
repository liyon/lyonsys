package com.lyonsys.cyclehire;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by yong on 16/06/2016.
 */
public class PaymentService {

    private double pricePerHour = 1;

    private long millSecondsInHour = 1000 * 3600;

    private Map<String, AtomicReference<BigDecimal>> ledger = new HashMap();
    {
        ledger.put("CycleHireCharge", new AtomicReference(BigDecimal.ZERO));
    }

    private void receiveCycleHireCharge(double charge)
    {
        boolean done = false;
        while (!done) {
            BigDecimal oldValue = ledger.get("CycleHireCharge").get();
            done = ledger.get("CycleHireCharge").compareAndSet(oldValue, oldValue.add(BigDecimal.valueOf(charge)));
        }
    }

    public void makePayment(Journey journey)
    {
       long hours = (journey.getFinishTime().getTime() - journey.getStartTime().getTime())/ millSecondsInHour;
        if (hours >1)
        {
            double charge = pricePerHour * hours;
            journey.getUser().addToBalance(-charge);
            this.receiveCycleHireCharge(charge);
        }
    }

    public double getCycleHireRevenue(){
        return ledger.get("CycleHireCharge").get().doubleValue();
    }
}
