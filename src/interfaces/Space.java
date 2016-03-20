package interfaces;

public interface Space extends Cloneable {
	public int getRoomsCount();
	public void setRoomsCount(int roomsCount);
	
	public float getArea();
	public void setArea(float area);
	
	public Object clone() throws CloneNotSupportedException;
}
