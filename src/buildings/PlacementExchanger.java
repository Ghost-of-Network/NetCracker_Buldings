package buildings;

import interfaces.Space;
import interfaces.Floor;
import exceptions.InexchangeableSpacesException;
import exceptions.InexchangeableFloorsException;
import exceptions.SpaceIndexOutOfBoundsException;
import interfaces.Building;

public class PlacementExchanger {

    public PlacementExchanger() {

    }

    public static boolean checkChangableSpace(Space space1, Space space2) {
        return (space1.getRoomsCount() == space2.getRoomsCount() && space1.getArea() == space2.getArea());
    }

    public static boolean checkChangableFloor(Floor floor1, Floor floor2) {
        return (floor1.totalArea() == floor2.totalArea() && floor1.spacesCount() == floor2.spacesCount());
    }

    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2, int index2) throws InexchangeableSpacesException {
        if (index1 < 0 || index2 < 0) {
            throw new SpaceIndexOutOfBoundsException("Index is less than zero");
        }
        if (checkChangableSpace(floor1.getSpace(index1), floor2.getSpace(index2))) {
            throw new InexchangeableSpacesException("Spaces with index " + index1 + "and" + index2 + "can not exchange");
        } else {

            Space temp;
            temp = floor1.getSpace(index1);
            floor1.changeSpace(floor2.getSpace(index2), index1);
            floor2.changeSpace(temp, index2);
        }
    }

    public static void exchangeBuildingFloors(Building building1, int index1, Building building2, int index2) throws InexchangeableFloorsException {
        if (index1 < 0 || index2 < 0) {
            throw new SpaceIndexOutOfBoundsException("Index is less than zero");
        }
        if (checkChangableFloor(building1.getFloor(index1), building1.getFloor(index2))) {
            throw new InexchangeableFloorsException("Floors with index " + index1 + "and" + index2 + "can not exchange");
        } else {
            Floor temp;
            temp = building1.getFloor(index1);
            building1.changeFloor(building2.getFloor(index2), index1);
            building2.changeFloor(temp, index2);
        }
    }
}
