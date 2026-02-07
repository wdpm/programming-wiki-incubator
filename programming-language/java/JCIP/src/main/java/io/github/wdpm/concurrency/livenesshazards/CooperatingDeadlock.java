package io.github.wdpm.concurrency.livenesshazards;

import io.github.wdpm.concurrency.annotations.GuardedBy;
import io.github.wdpm.concurrency.composingobjects.Point;

import java.util.HashSet;
import java.util.Set;

/**
 * CooperatingDeadlock
 * <p/>
 * Lock-ordering deadlock between cooperating objects
 * <p>
 * 两个锁以不同的顺序被线程占有，发生死锁风险
 *
 * @author Brian Goetz and Tim Peierls
 */
public class CooperatingDeadlock {
    // Warning: deadlock-prone!

    /**
     * 出租车对象
     */
    class Taxi {
        @GuardedBy("this")
        private Point location, destination;
        private final Dispatcher dispatcher;

        public Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        public synchronized Point getLocation() {
            return location;
        }

        // setLocation() 先获取taxi的锁，然后获取dispatcher的锁
        public synchronized void setLocation(Point location) {
            this.location = location;
            if (location.equals(destination)) dispatcher.notifyAvailable(this);
        }

        public synchronized Point getDestination() {
            return destination;
        }

        public synchronized void setDestination(Point destination) {
            this.destination = destination;
        }
    }

    /**
     * 出租车车队，调度出租车
     */
    class Dispatcher {
        @GuardedBy("this")
        private final Set<Taxi> taxis;
        @GuardedBy("this")
        private final Set<Taxi> availableTaxis;

        public Dispatcher() {
            taxis = new HashSet<Taxi>();
            availableTaxis = new HashSet<Taxi>();
        }

        public synchronized void notifyAvailable(Taxi taxi) {
            availableTaxis.add(taxi);
        }

        // getImage()先获取Dispatcher的锁，然后获取taxi的锁。
        public synchronized Image getImage() {
            Image image = new Image();
            for (Taxi t : taxis)
                image.drawMarker(t.getLocation());
            return image;
        }
    }

    class Image {
        public void drawMarker(Point p) {
        }
    }
}
