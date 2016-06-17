package com.lyonsys.cyclehire;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by yong on 13/06/2016.
 */
public class DockingPoint {

    private Cycle cycle;


    public DockingPoint() {
    }


    public synchronized Cycle takeCycleIfAvailable()
    {
        Cycle aCycle = null;
        if (this.cycle != null)
        {
            aCycle = this.cycle;
            this.cycle = null;
        }
        return aCycle;
    }

    public synchronized boolean parkCycleIfEmpty(Cycle cycle)
    {
        if (this.cycle == null)
        {
            this.cycle = cycle;
            return true;
        }
        return false;
    }
}
