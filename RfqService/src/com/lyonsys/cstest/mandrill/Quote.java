package com.lyonsys.cstest.mandrill;

/**
 * Created by yong on 16/04/2016.
 */
public class Quote {
    public final double bid;

    public double getAsk() {
        return ask;
    }

    public double getBid() {
        return bid;
    }

    public final double ask;

    public Quote(double bid, double ask) {
        this.bid = bid;
        this.ask = ask;
    }

}
