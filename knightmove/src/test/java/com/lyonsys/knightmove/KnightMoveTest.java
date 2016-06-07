package com.lyonsys.knightmove;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by yong on 06/06/2016.
 */
public class KnightMoveTest {


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

    @Test
    public void getNumberOfRoutes() throws Exception {
        KnightMove knightMove = new KnightMove(keyBoard , 1);
        assertEquals(18, knightMove.getNumberOfRoutes());

          knightMove = new KnightMove(keyBoard , 2);
        assertEquals(60, knightMove.getNumberOfRoutes());

          knightMove = new KnightMove(keyBoard , 10);
        assertEquals(1013398, knightMove.getNumberOfRoutes());

        knightMove = new KnightMove(keyBoard , 16);
        assertEquals(1195650888, knightMove.getNumberOfRoutes());

        knightMove = new KnightMove(keyBoard , 32);
        //assertEquals(1195650888, knightMove.getNumberOfRoutes());
    }

}