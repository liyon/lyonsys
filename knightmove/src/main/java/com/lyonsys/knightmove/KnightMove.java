package com.lyonsys.knightmove;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

/**
 * Created by yong on 02/06/2016.
 */
public class KnightMove {

    private Set<Key> keyBoard;
    private int steps;

    public KnightMove(Set<Key> keyBoard, int numberOfSequence) {
        this.keyBoard = keyBoard;
        this.steps = numberOfSequence;
        //Only need build valid knight move for each key once.
        keyBoard.stream().forEach(p -> p.setNextMove(getValidKnightMove(p)));
    }

    public long getNumberOfRoutes() {
        AtomicLong numberOfRoutes = new AtomicLong();
        keyBoard.stream().parallel().forEach(p ->
        {
            this.traverse(new Path(p, null), numberOfRoutes);
        });

        return numberOfRoutes.longValue();
    }

    private Key[] getValidKnightMove(Key fromKey) {

        int x = fromKey.getX();
        int y = fromKey.getY();

        List<Key> nextNodes = new ArrayList<>(8);
        addNextMoveIfValid(nextNodes, x + 2, y + 1);
        addNextMoveIfValid(nextNodes, x + 2, y - 1);
        addNextMoveIfValid(nextNodes, x - 2, y + 1);
        addNextMoveIfValid(nextNodes, x - 2, y - 1);
        addNextMoveIfValid(nextNodes, x + 1, y + 2);
        addNextMoveIfValid(nextNodes, x + 1, y - 2);
        addNextMoveIfValid(nextNodes, x - 1, y + 2);
        addNextMoveIfValid(nextNodes, x - 1, y - 2);

        return nextNodes.toArray(new Key[]{});

    }

    private void addNextMoveIfValid(List<Key> nodes, int x, int y) {
        Optional<Key> key = keyBoard.stream().filter(k -> k.getX() == x && k.getY() == y).findAny();
        if (key.isPresent()) {
            nodes.add(key.get());
        }
    }


    private void traverse(Path path, AtomicLong numberOfPaths) {

        // finish earlier if number of vowels more than 2
        if (path.getNumberOfVowels() > 2) {
            return;
        }
        if (path.getLevel() == steps ) {
            numberOfPaths.incrementAndGet();
            return;
        }
        //Arrays.stream(path.getKey().getNextMove()).forEach(p->
        for (Key p : path.getKey().getNextMove()) {
            Path nextPath = new Path(p, path);
            traverse(nextPath, numberOfPaths);
        }
        ;
    }

}

