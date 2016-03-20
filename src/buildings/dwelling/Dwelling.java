package buildings.dwelling;

import java.io.Serializable;
import java.util.Iterator;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import exceptions.FloorIndexOutOfBoundsException;
import exceptions.SpaceIndexOutOfBoundsException;
import java.util.Arrays;

public class Dwelling implements Building, Serializable {

    Floor[] floors;

    public Dwelling(int floorsCount, int[] spacesCounts) {
        floors = new DwellingFloor[floorsCount];
        for (int i = 0; i < floorsCount; i++) {
            floors[i] = new DwellingFloor(spacesCounts[i]);
        }
    }

    public Dwelling(Floor[] floors) {
        this.floors = floors;
    }

    @Override
    public int floorsCount() {
        return floors.length;
    }

    @Override
    public int spacesCount() {
        int total = 0;
        for (Floor floor : floors) {
            total += floor.spacesCount();
        }
        return total;
    }

    @Override
    public int roomsCount() {
        int total = 0;
        for (Floor floor : floors) {
            total += floor.roomsCount();
        }
        return total;
    }

    @Override
    public float totalArea() {
        float total = 0;
        for (Floor floor : floors) {
            total += floor.totalArea();
        }
        return total;
    }

    @Override
    public Floor[] floorsArray() {
        return floors;
    }

    @Override
    public Floor getFloor(int number) throws FloorIndexOutOfBoundsException {
        if (number < 0 || number >= floors.length) {
            throw new FloorIndexOutOfBoundsException("floor with number " + number + "is invalid");
        }
        return floors[number];
    }

    public Floor[] getFloors() {
        return this.floors;
    }

    @Override
    public void changeFloor(Floor floor, int number) throws FloorIndexOutOfBoundsException {
        if (number < 0 || number >= floors.length) {
            throw new FloorIndexOutOfBoundsException("floor with number " + number + "is invalid");
        }
        this.floors[number] = floor;
    }

    public void setFloors(Floor[] floors) {
        this.floors = floors;
    }

    public int getFloorsCount() {
        return floors.length;
    }

    private class SpacePosition {

        int spaceNumber;
        int floorNumber;

        public SpacePosition(int spaceNumber, int floorNumber) {
            this.spaceNumber = spaceNumber;
            this.floorNumber = floorNumber;
        }

        public int getSpaceNumber() {
            return spaceNumber;
        }

        public int getFloorNumber() {
            return floorNumber;
        }
    }

    private SpacePosition getSpacePosition(int spaceNumber) {
        if (spaceNumber < 0 || spaceNumber >= spacesCount()) {
            return null;
        }

        int floorNumber = 0;
        int spacesCount = 0;

        for (int i = 0; i < floors.length; i++) {
            spacesCount += floors[i].spacesCount();
            if (spacesCount > spaceNumber) {
                floorNumber = i;
                break;
            }
        }
        return new SpacePosition(spaceNumber + floors[floorNumber].spacesCount() - spacesCount, floorNumber);
    }

    @Override
    public Space getSpace(int number) throws SpaceIndexOutOfBoundsException {
        SpacePosition position = getSpacePosition(number);
        if (position == null) {
            throw new SpaceIndexOutOfBoundsException("space with number " + number + "is invalid");
        }
        return floors[position.getFloorNumber()].getSpace(position.getSpaceNumber());
    }

    @Override
    public void removeSpace(int number) throws SpaceIndexOutOfBoundsException {
        SpacePosition position = getSpacePosition(number);
        if (position == null) {
            throw new SpaceIndexOutOfBoundsException("space with number " + number + "is invalid");
        }
        floors[position.getFloorNumber()].removeSpace(position.getSpaceNumber());
    }

    @Override
    public void changeSpace(Space space, int number) throws SpaceIndexOutOfBoundsException {
        SpacePosition position = getSpacePosition(number);
        if (position == null) {
            throw new SpaceIndexOutOfBoundsException("space with number " + number + "is invalid");
        }
        floors[position.getFloorNumber()].changeSpace(space, position.getSpaceNumber());
    }

    @Override
    public void insertSpace(Space space, int number) throws SpaceIndexOutOfBoundsException {
        SpacePosition position = getSpacePosition(number);
        if (position == null) {
            if (number == spacesCount()) {
                floors[floors.length - 1].insertSpace(space, floors[floors.length - 1].spacesCount());
            } else {
                throw new SpaceIndexOutOfBoundsException("space with number " + number + "is invalid");
            }
        } else {
            floors[position.getFloorNumber()].insertSpace(space, position.getSpaceNumber());
        }
    }

    @Override
    public Space getBestSpace() {
        Space bestSpace = floors[0].getSpace(0);
        for (Floor floor : floors) {
            for (int j = 0; j < floor.spacesCount(); j++) {
                if (bestSpace.getArea() < floor.getSpace(j).getArea()) {
                    bestSpace = floor.getSpace(j);
                }
            }
        }
        return bestSpace;
    }

    @Override
    public Space[] getSortedSpaces() {
        Space[] sortedSpaces = new Flat[spacesCount()];

        int count = 0;
        for (Floor floor : floors) {
            for (int j = 0; j < floor.spacesCount(); j++) {
                sortedSpaces[count++] = floor.getSpace(j);
            }
        }

        for (int i = 0; i < sortedSpaces.length; i++) {
            for (int j = i + 1; j < sortedSpaces.length; j++) {
                if (sortedSpaces[j].getArea() > sortedSpaces[i].getArea()) {
                    Space tmp = sortedSpaces[i];
                    sortedSpaces[i] = sortedSpaces[j];
                    sortedSpaces[j] = tmp;
                }
            }
        }

        return sortedSpaces;
    }

    private class BuildingIterator implements Iterator {

        int index;

        public BuildingIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < floors.length;
        }

        @Override
        public Object next() {
            return floors[index++];
        }

        @Override
        public void remove() {
        }
    }

    @Override
    public Iterator iterator() {
        return new BuildingIterator();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Dwelling (" + floors.length);
        for (Floor floor : floors) {
            s.append(", ").append(floor.toString());
        }
        return s.append(")").toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object.getClass() != Dwelling.class) {
            return false;
        }

        Dwelling dwelling = (Dwelling) object;

        if (!(dwelling.floorsCount() == floors.length)) {
            return false;
        }
        for (int i = 0; i < floors.length; i++) {
            if (!dwelling.getFloor(i).equals(floors[i])) {
                return false;
            }
        }
        return true;
    }

    /*  @Override
     public int hashCode() {
     int hash = floors.length;
     for (Floor floor : floors) {
     hash ^= floor.hashCode();
     }
     return hash ^ 22;
     }*/
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Arrays.deepHashCode(this.floors);
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Dwelling result = (Dwelling) super.clone();
        result.floors = floors.clone();
        for (int i = 0; i < floors.length; i++) {
            result.changeFloor((Floor) floors[i].clone(), i);
        }
        return result;
    }
}
