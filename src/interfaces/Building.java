package interfaces;

import java.util.Iterator;

public interface Building extends Iterable<Floor> {
	public int floorsCount();
	public int spacesCount();
	public int roomsCount();
	public float totalArea();
	
	public Floor[] floorsArray();
	public Floor getFloor(int floorNumber);
	// public Floor[] getFloors();
	public void changeFloor(Floor floor, int floorNumber);
	
	public Space getSpace(int spaceNumber);
	public void changeSpace(Space space, int spaceNumber);
	public void insertSpace(Space space, int spaceNumber);
	public void removeSpace(int spaceNumber);
	
	public Space getBestSpace();
	public Space[] getSortedSpaces();
	
	public Iterator<Floor> iterator();
	public Object clone() throws CloneNotSupportedException;
}
