package io.github.wdpm.concurrency.composingobjects;


import io.github.wdpm.concurrency.annotations.Immutable;

/**
 * Point
 * <p/>
 * Immutable Point class used by DelegatingVehicleTracker
 *
 * @author Brian Goetz and Tim Peierls
 */
@Immutable
public class Point {
    // final -> Immutable
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
