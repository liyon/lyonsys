package com.lyonsys.knightmove;

import java.util.List;
import java.util.Set;

/**
 * Created by yong on 02/06/2016.
 */
public class Key {

    private char keyChar;
    private int x;
    private int y;


    public synchronized Key[] getNextMove() {
        return nextMove;
    }

    public synchronized void setNextMove(Key[] nextMove) {
        this.nextMove = nextMove;
    }

    private Key[] nextMove;

    public Key(char keyChar, int x, int y) {
        this.keyChar = keyChar;
        this.x = x;
        this.y = y;
    }

    public char getKeyChar() {
        return keyChar;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isUpperCaseVowel()
    {
        return keyChar == 'A' || keyChar == 'E'|| keyChar == 'I' || keyChar == 'O' || keyChar == 'U';
    }

}


