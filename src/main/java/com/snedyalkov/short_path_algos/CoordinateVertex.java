package com.snedyalkov.short_path_algos;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CoordinateVertex{

    private static AtomicInteger counter = new AtomicInteger(0);
    private static Random random = new Random();

    private int id;
    private int x;
    private int y;

    public CoordinateVertex(){
        //Integer id, String x, String y
        this.id = counter.getAndIncrement();
        this.x = random.nextInt(0, 101);
        this.y = random.nextInt(0, 101);
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }
}