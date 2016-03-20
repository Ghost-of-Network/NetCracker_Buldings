package buildings;

import java.util.Iterator;

import interfaces.Floor;
import interfaces.Space;
import java.util.Objects;

public class SynchronizedFloor implements Floor {

    Floor floor;

    public SynchronizedFloor(Floor floor) {
        this.floor = floor;
    }

    @Override
    public synchronized int spacesCount() {
        return floor.spacesCount();
    }

    @Override
    public synchronized int roomsCount() {
        return floor.roomsCount();
    }

    @Override
    public synchronized float totalArea() {
        return floor.totalArea();
    }

    @Override
    public synchronized Space[] getSpaces() {
        return floor.getSpaces();
    }

    @Override
    public synchronized Space getSpace(int spaceNumber) {
        return floor.getSpace(spaceNumber);
    }

    @Override
    public synchronized Space getBestSpace() {
        return floor.getBestSpace();
    }

    @Override
    public synchronized void changeSpace(Space space, int spaceNumber) {
        floor.changeSpace(space, spaceNumber);
    }

    @Override
    public synchronized void insertSpace(Space space, int spaceNumber) {
        floor.insertSpace(space, spaceNumber);
    }

    @Override
    public synchronized void removeSpace(int spaceNumber) {
        floor.removeSpace(spaceNumber);
    }

    @Override
    public Iterator iterator() {
        return floor.iterator();
    }

    @Override
    public synchronized Object clone() throws CloneNotSupportedException {
        SynchronizedFloor syncFloor = (SynchronizedFloor) super.clone();
        syncFloor.floor = (Floor) floor.clone();
        return syncFloor;
    }

    @Override
    public synchronized String toString() {
        return floor.toString();
    }

    @Override
    public synchronized int hashCode() {
        return floor.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SynchronizedFloor other = (SynchronizedFloor) obj;
        if (!Objects.equals(this.floor, other.floor)) {
            return false;
        }
        return true;
    }
}
