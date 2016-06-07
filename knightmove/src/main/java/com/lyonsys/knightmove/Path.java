package com.lyonsys.knightmove;

/**
 * Created by yong on 04/06/2016.
 */
public class Path {

    private int numberOfVowels;
    private int level;
    private Key key;

    public Path(Key key, Path parent) {
        this.key = key;
        int vowelNumber = this.key.isUpperCaseVowel() ? 1 : 0;
        if (parent == null) {
            this.level = 0;
            this.numberOfVowels = vowelNumber;
        } else {
            this.level = parent.getLevel() + 1;
            this.numberOfVowels = parent.numberOfVowels + vowelNumber;
        }
    }

    public int getNumberOfVowels() {
        return numberOfVowels;
    }

    public int getLevel() {
        return level;
    }

    public Key getKey() {
        return key;
    }
}
