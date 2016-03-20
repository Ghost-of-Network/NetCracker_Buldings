package buildings.office;

import java.io.Serializable;

import interfaces.Space;
import exceptions.InvalidSpaceAreaException;
import exceptions.InvalidRoomsCountException;

public class Office implements Space, Serializable, Cloneable {

    final static float defaultArea = 250;
    final static int defaultRoomsCount = 1;

    private float area;
    private int roomsCount;

    public Office() {
        area = defaultArea;
        roomsCount = defaultRoomsCount;
    }

    public Office(float area) {
        setArea(area);
        roomsCount = defaultRoomsCount;
    }

    public Office(float area, int roomsCount) {
        setArea(area);
        setRoomsCount(roomsCount);
    }

    @Override
    public float getArea() {
        return area;
    }

    @Override
    public void setArea(float area) throws InvalidSpaceAreaException {
        if (area < 0) {
            throw new InvalidSpaceAreaException("area " + area + "is invalid.");
        }
        this.area = area;
    }

    @Override
    public int getRoomsCount() {
        return roomsCount;
    }

    @Override
    public void setRoomsCount(int roomsCount) throws InvalidRoomsCountException {
        if (roomsCount < 0) {
            throw new InvalidRoomsCountException("rooms count " + roomsCount + "is invalid.");
        }
        this.roomsCount = roomsCount;
    }

    @Override
    public String toString() {
        return String.format("Office (%.1f|%d)", area, roomsCount);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object.getClass() != Office.class) {
            return false;
        }
        Office office = (Office) object;

        return (this.area == office.area) && (this.roomsCount == office.roomsCount);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Float.floatToIntBits(this.area);
        hash = 41 * hash + this.roomsCount;
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
