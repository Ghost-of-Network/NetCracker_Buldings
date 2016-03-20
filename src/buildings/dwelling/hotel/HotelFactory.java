package buildings.dwelling.hotel;

import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import interfaces.Building;
import interfaces.BuildingFactory;
import interfaces.Floor;
import interfaces.Space;

public class HotelFactory implements BuildingFactory {
        @Override
	public Space createSpace(float area) {
		return (Space) new Flat(area);
	}
	
        @Override
	public Space createSpace(int roomsCount, float area) {
		return (Space) new Flat(area, roomsCount);
	}

        @Override

        public Floor createFloor(int spacesCount) {
		return (Floor) new DwellingFloor(spacesCount);
	}

        @Override
	public Floor createFloor(Space[] spaces) {
		return (Floor) new DwellingFloor(spaces);
	}

        @Override
	public Building createBuilding(int floorsCount, int[] spacesCounts) {
		return (Building) new Dwelling(floorsCount, spacesCounts);
	}

        @Override
	public Building createBuilding(Floor[] floors) {
		return (Building) new Dwelling(floors);
	}
}
