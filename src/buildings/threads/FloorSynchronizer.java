package buildings.threads;

import interfaces.Floor;
import interfaces.Space;

public class FloorSynchronizer {

    Floor floor;
    volatile int current = 0;	// номер ремонт./убир. помещения на данный момент
    Object lock = new Object();	// внешний синхронизатор
    boolean repaired = false;	// индикатор проведенного ремонта. разрешает уборку помещения

    public FloorSynchronizer(Floor floor) {
        this.floor = floor;
    }

    public boolean canClean() {
        return current < floor.spacesCount();
    }

    public boolean canRepair() {
        return (!repaired && current < floor.spacesCount())
                || (repaired && current < floor.spacesCount() - 1);
    }

    public void clean() throws InterruptedException {
        Space space;
        synchronized (lock) {
            if (!canClean()) {
                throw new InterruptedException();
            }
            while (!repaired) {
                lock.wait();
            }
            space = floor.getSpace(current);
            System.out.println("Cleaning room number " + current + " with total area " + space.getArea()
                    + " square meters");
            repaired = false;
            current++;
            lock.notifyAll();
        }
    }

    public void repair() throws InterruptedException {
        Space space;
        synchronized (lock) {
            if (!canRepair()) {
                throw new InterruptedException();
            }
            while (repaired) {
                lock.wait();
            }
            space = floor.getSpace(current);
            System.out.println("Repairing room number " + current + " with total area " + space.getArea()
                    + " square meters");
            repaired = true;
            lock.notifyAll();
        }
    }

    public Floor getFloor() {
        return floor;
    }
}
