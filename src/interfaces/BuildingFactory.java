package interfaces;

public interface BuildingFactory {
	public Space createSpace(float area);
	public Space createSpace(int roomsCount, float area);
	
	public Floor createFloor(int spacesCount);
	public Floor createFloor(Space[] spaces);
	
	public Building createBuilding(int floorsCount, int[] spacesCounts);
	public Building createBuilding(Floor[] floors);
}
