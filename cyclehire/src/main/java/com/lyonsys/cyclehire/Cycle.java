package com.lyonsys.cyclehire;

/**
 * Created by yong on 13/06/2016.
 */
public class Cycle {
    private String cycleId;
    private CycleSatus status;

}

enum CycleSatus{
    Park, Running, Broken;
}