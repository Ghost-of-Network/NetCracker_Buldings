package buildings.dwelling;

import java.io.Serializable;

import interfaces.Space;
import exceptions.InvalidSpaceAreaException;
import exceptions.InvalidRoomsCountException;

public class Flat implements Space, Serializable, Cloneable {

    static final float defaultArea = 50;
    static final int defaultRoomsCount = 2;

    float area;
    int roomsCount;

    public Flat() {
        area = defaultArea;
        roomsCount = defaultRoomsCount;
    }

    public Flat(float area) {
        setArea(area);
        roomsCount = defaultRoomsCount;
    }

    public Flat(float area, int roomsCount) {
        setArea(area);
        setRoomsCount(roomsCount);
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
    public String toString() {
        return String.format("Flat (%.1f/%d)", area, roomsCount);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object.getClass() != Flat.class) {
            return false;
        }
        Flat flat = (Flat) object;

        return (this.area == flat.area) && (this.roomsCount == flat.roomsCount);
    }

    /*@Override
     public int hashCode() {
     return 22 ^ roomsCount ^ Float.floatToIntBits(area);
     }*/
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Float.floatToIntBits(this.area);
        hash = 37 * hash + this.roomsCount;
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
