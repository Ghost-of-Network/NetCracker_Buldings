package buildings.office;

import interfaces.Building;
import interfaces.BuildingFactory;
import interfaces.Floor;
import interfaces.Space;

public class OfficeFactory implements BuildingFactory {
	@Override
	public Space createSpace(float area) {
		return new Office(area);
	}
	
	@Override
	public Space createSpace(int roomsCount, float area) {
		return new Office(area, roomsCount);
	}

	@Override
	public Floor createFloor(int spacesCount) {
		return new OfficeFloor(spacesCount);
	}

	@Override
	public Floor createFloor(Space[] spaces) {
		return new OfficeFloor(spaces);
	}

	@Override
	public Building createBuilding(int floorsCount, int[] spacesCounts) {
		return new OfficeBuilding(floorsCount, spacesCounts);
	}

	@Override
	public Building createBuilding(Floor[] floors) {
		return new OfficeBuilding(floors);
	}
}
