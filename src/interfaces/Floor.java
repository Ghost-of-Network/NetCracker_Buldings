package interfaces;

import java.util.Iterator;

public interface Floor extends Cloneable {
	public int spacesCount();
	public int roomsCount();
	public float totalArea();
	
	public Space[] getSpaces();
	public Space getSpace(int spaceNumber);
	public Space getBestSpace();
	
	public void changeSpace(Space space, int spaceNumber);
	public void insertSpace(Space space, int spaceNumber);
	public void removeSpace(int spaceNumber);
	
	public Iterator iterator();
	public Object clone() throws CloneNotSupportedException;
}
