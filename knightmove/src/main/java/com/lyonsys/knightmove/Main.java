package com.lyonsys.knightmove;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yong on 02/06/2016.
 */
public class Main {

    private static final Set<Key> keyBoard = new HashSet();;
    static
    {
        addKey(keyBoard, 0, 0,  "ABCDE");
        addKey(keyBoard, 1, 0,  "FGHIJ");
        addKey(keyBoard, 2, 0,  "KLMNO");
        addKey(keyBoard, 3, 1,  "123");
    }

    private static void addKey(Set<Key> keyBoard, int row, int startIndex, String chars)
    {
        int i = 0;
        for (char c: chars.toCharArray())
        {
           keyBoard.add(new Key(c, startIndex + i,row)) ;
            i++;
        }
    }

    public  static void main(String[] args)
    {
        int n = 10;
        if (args.length > 0)
        {
            n = Integer.parseInt(args[0]);
        }
        System.out.println("Number of steps = " + n);

        KnightMove knightMove = new KnightMove(keyBoard , n);
        long numberOfRoute = knightMove.getNumberOfRoutes();

        System.out.print("Number of routes = " + numberOfRoute);
    }


}
