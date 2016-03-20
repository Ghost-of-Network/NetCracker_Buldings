package buildings.dwelling.hotel;

import buildings.dwelling.Dwelling;
import buildings.dwelling.Flat;
import interfaces.Floor;
import interfaces.Space;
import java.io.Serializable;

public class Hotel extends Dwelling implements Serializable {

    double[] coeff = {0.25, 0.5, 1, 1.25, 1.5};

    public Hotel(int floorsCount, int[] spacesCounts) {
        super(floorsCount, spacesCounts);
    }

    public Hotel(Floor[] floors) {
        super(floors);
    }

    public int hotelRating() {
        int maxHotelRating = 0;

        Floor[] hotelFloor = super.getFloors();
        for (Floor hotelFloor1 : hotelFloor) {
            if (hotelFloor1.getClass() == HotelFloor.class) {
                HotelFloor currentHotelFloor = (HotelFloor) hotelFloor1;
                if (currentHotelFloor.getStars() > maxHotelRating) {
                    maxHotelRating = currentHotelFloor.getStars();
                }
            }
        }
        return maxHotelRating;
    }

    @Override
    public Space getBestSpace() {
        Floor[] hotelFloor = super.getFloors();
        // Space bestSpace = new Space()
        Space bestSpace = new Flat();
        int bestStartRating = 1;

        for (Floor hotelFloor1 : hotelFloor) {
            if (hotelFloor1.getClass() == HotelFloor.class) {
                HotelFloor currentHotelFloor = (HotelFloor) hotelFloor1;
                Space bestFloorSpace = currentHotelFloor.getBestSpace();
                if (bestFloorSpace.getArea() * coeff[currentHotelFloor.getStars() - 1] > bestSpace.getArea() * coeff[bestStartRating - 1]) {
                    bestSpace = bestFloorSpace;
                    bestStartRating = currentHotelFloor.getStars();
                }
            }
        }
        return bestSpace;
    }

    @Override
    public String toString() {
        Floor[] hotelFloor = super.getFloors();
        StringBuilder s = new StringBuilder("Dwelling (" + hotelFloor.length);
        for (Floor floor : hotelFloor) {
            s.append(", ").append(floor.toString());
        }
        return s.append(")").toString();
    }

    @Override
    public boolean equals(Object object) {
        Floor[] hotelFloor = super.getFloors();

        if (object == this) {
            return true;
        }
        if (object.getClass() != Dwelling.class) {
            return false;
        }

        Dwelling dwelling = (Dwelling) object;

        if (!(dwelling.getFloorsCount() == hotelFloor.length)) {
            return false;
        }
        for (int i = 0; i < hotelFloor.length; i++) {
            if (!dwelling.getFloor(i).equals(hotelFloor[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        Floor[] hotelFloor = super.getFloors();
        int hash = hotelFloor.length;
        for (Floor floor : hotelFloor) {
            hash ^= floor.hashCode();
        }
        return hash ^ 23;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Floor[] hotelfloors = super.getFloors();
        Dwelling result = (Dwelling) super.clone();
        result.setFloors(hotelfloors.clone());
        for (int i = 0; i < hotelfloors.length; i++) {
            result.changeFloor((Floor) hotelfloors[i].clone(), i);
        }
        return result;
    }
}
