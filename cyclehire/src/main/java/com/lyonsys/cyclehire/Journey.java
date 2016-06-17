package com.lyonsys.cyclehire;

import com.lyonsys.cyclehire.DockingPoint;
import com.lyonsys.cyclehire.client.User;

import java.util.Date;

/**
 * Created by yong on 14/06/2016.
 */
public class Journey {
    private User user;


    private Cycle cycle;
    private DockingStation start;
    private DockingStation end;
    private Date startTime;
    private Date finishTime;

    public Journey(User user, Cycle cycle, DockingStation start) {
        this.user = user;
        this.cycle = cycle;
        this.start = start;
        this.startTime = new Date();
    }

    public boolean isFinished()
    {
        return this.finishTime != null;
    }
    public void end(DockingStation endStation)
    {
        this.end = endStation;
        this.finishTime = new Date();
    }

    public Cycle getCycle() {
        return cycle;
    }

    public User getUser() {
        return user;
    }

    public DockingStation getStart() {
        return start;
    }

    public DockingStation getEnd() {
        return end;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "user=" + user +
                ", cycle=" + cycle +
                ", start=" + start +
                ", end=" + end +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                '}';
    }
}
