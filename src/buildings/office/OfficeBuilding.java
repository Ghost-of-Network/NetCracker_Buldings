package buildings.office;

import java.io.Serializable;
import java.util.Iterator;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import exceptions.FloorIndexOutOfBoundsException;
import exceptions.SpaceIndexOutOfBoundsException;

class BuildingNode implements Serializable {
	Floor floor;
	BuildingNode prev;
	BuildingNode next;
	
	public BuildingNode() { }
	public BuildingNode(Floor floor) {
		this.floor = floor;
	}
	
	public Floor getFloor() {
		return floor;
	}
	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	public BuildingNode getPrev() {
		return prev;
	}
	public void setPrev(BuildingNode prev) {
		this.prev = prev;
	}
	public BuildingNode getNext() {
		return next;
	}
	public void setNext(BuildingNode next) {
		this.next = next;
	}
}

public class OfficeBuilding implements Building, Serializable {
	BuildingNode headNode;

	private void addNode(BuildingNode node, int number) {	
		if (number == 0) {
			if (floorsCount() != 0) {
				node.setNext(headNode.getNext());
				node.setPrev(headNode.getNext().getPrev());
				
				headNode.getNext().getPrev().setNext(node);
				headNode.getNext().setPrev(node);
			}
			else {
				node.setNext(node);
				node.setPrev(node);
			}
			headNode.setNext(node);
		}
		else {
			BuildingNode current = getNode(number - 1);
			
			node.setNext(current.getNext());
			node.setPrev(current);
			
			current.getNext().setPrev(node);
			current.setNext(node);
		}
	}
	@SuppressWarnings("unused")
	private void removeNode(int number) {
		BuildingNode rmNode;
		
		rmNode = getNode(number);
		
		rmNode.getPrev().setNext(rmNode.getNext());
		rmNode.getNext().setPrev(rmNode.getPrev());
		
		if (number == 0) {
			if (floorsCount() != 0)
				headNode.setNext(rmNode.getNext());
			else
				headNode.setNext(null);
		}
		rmNode = null;
	}
	
	private BuildingNode getNode(int number) {
		BuildingNode node = headNode.getNext();
		if (node == null) return null;
		for (int i = 0; i != number; i++)
			node = node.getNext();
		return node;
	}
	
	
	public OfficeBuilding(int floorsCount, int[] spacesCount) {
		headNode = new BuildingNode();
		for (int i = 0; i < floorsCount; i++)
			addNode(new BuildingNode(new OfficeFloor(spacesCount[i])), i);
	}
	public OfficeBuilding(Floor[] floors) {
		headNode = new BuildingNode();
		for (int i = 0; i < floors.length; i++)
			addNode(new BuildingNode(floors[i]), i);
	}
	
	
	@Override
	public int floorsCount() {
		BuildingNode node = headNode.getNext();
		if (node == null) return 0;
		int total = 1;		
		for (node = node.getNext(); node != headNode.getNext(); node = node.getNext())
			total++;
		return total;
	}
	@Override
	public int spacesCount() {
		BuildingNode node = headNode.getNext();
		if (node == null) return 0;
		int total = node.getFloor().spacesCount();
		for (node = node.getNext(); node != headNode.getNext(); node = node.getNext())
			total += node.getFloor().spacesCount();
		return total;
	}
	@Override
	public int roomsCount() {
		BuildingNode node = headNode.getNext();
		if (node == null) return 0;
		int total = node.getFloor().roomsCount();
		for (node = node.getNext(); node != headNode.getNext(); node = node.getNext())
			total += node.getFloor().roomsCount();
		return total;		
	}
	
	
	private class SpacePosition {
		int spaceNumber;
		int floorNumber;
		
		public SpacePosition(int spaceNumber, int floorNumber) {
			this.spaceNumber = spaceNumber;
			this.floorNumber = floorNumber;
		}
		
		public int getSpaceNumber() {
			return spaceNumber;
		}
		public int getFloorNumber() {
			return floorNumber;
		}
	}
	
	private SpacePosition getSpacePosition(int spaceNumber) {
		BuildingNode node = headNode.getNext();
		if (node == null) return null;
		
		int spacesCount = node.getFloor().spacesCount();
		int floorNumber = 0;
		for (node = node.getNext(); spacesCount <= spaceNumber; node = node.getNext()) {
			spacesCount += node.getFloor().spacesCount();
			floorNumber++;
		}
		return new SpacePosition(spaceNumber + getNode(floorNumber).getFloor().spacesCount() - spacesCount, floorNumber);
	}
	
	public int tSpacesCount() {
		Iterator i = iterator();
		int count = 0;
		while (i.hasNext()) {
			OfficeFloor floor = (OfficeFloor) i.next();
			count += floor.tSpacesCount();
		}
		return count;
	}
	
	@Override
	public Space getSpace(int number) throws SpaceIndexOutOfBoundsException {
		if (number < 0 || number >= spacesCount())
			throw new SpaceIndexOutOfBoundsException("Space with number " + number + "does not exist");
		SpacePosition position = getSpacePosition(number);
		return getNode(position.getFloorNumber()).getFloor().getSpace(position.getSpaceNumber());
	}
	@Override
	public void removeSpace(int number) throws SpaceIndexOutOfBoundsException {
		if (number < 0 || number > spacesCount())
			throw new SpaceIndexOutOfBoundsException("Space with number " + number + "cat not be removed");	
		SpacePosition position = getSpacePosition(number);
		getNode(position.getFloorNumber()).getFloor().removeSpace(position.getSpaceNumber());
	}
	@Override
	public void changeSpace(Space space, int number) throws SpaceIndexOutOfBoundsException {
		if (number < 0 || number >= spacesCount())
			throw new SpaceIndexOutOfBoundsException("Space with number " + number + "does not exist");
		SpacePosition position = getSpacePosition(number);
		getNode(position.getFloorNumber()).getFloor().changeSpace(space, position.getSpaceNumber());
	}
	@Override
	public void insertSpace(Space space, int number) throws SpaceIndexOutOfBoundsException {
		if (number < 0 || number > spacesCount())
			throw new SpaceIndexOutOfBoundsException("Space with number " + number + "cat not be inserted");	
		if (number == spacesCount())
			getNode(floorsCount() - 1).getFloor().insertSpace(space, getNode(floorsCount() - 1).getFloor().spacesCount() - 1);
		SpacePosition position = getSpacePosition(number);
		getNode(position.getFloorNumber()).getFloor().insertSpace(space, position.getSpaceNumber());
	}
	
	
	@Override
	public float totalArea() {
		BuildingNode node = headNode.getNext();
		if (node == null) return 0;
		float totalArea = node.getFloor().totalArea();
		for (node = node.getNext(); node != headNode.getNext(); node = node.getNext())
			totalArea += node.getFloor().totalArea();
		return totalArea;
	}
	@Override
	public Floor[] floorsArray() {
		BuildingNode node = headNode.getNext();
		if (node == null) return null;
		Floor[] floors = new Floor[floorsCount()];
		for (int i = 0; i < floorsCount(); i++) {
			floors[i] = node.getFloor();
			node = node.getNext();
		}
		return floors;
	}
	@Override
	public Floor getFloor(int number) throws FloorIndexOutOfBoundsException {	
		if (number < 0 || number >= floorsCount())
			throw new FloorIndexOutOfBoundsException("Floor with number " + number + " does not exist");
		return getNode(number).getFloor();
	}
	@Override
	public void changeFloor(Floor floor, int number) {
		if (number < 0 || number >= floorsCount())
			throw new FloorIndexOutOfBoundsException("Floor with number " + number + " does not exist");
		getNode(number).setFloor(floor);
	}
	
	@Override
	public Space getBestSpace() {
		BuildingNode node = headNode.getNext();
		if (node == null) return null;
		Space bestSpace = node.getFloor().getBestSpace();
		for (node = node.getNext(); node != headNode.getNext(); node = node.getNext()) {
			Space space = node.getFloor().getBestSpace();
			if (space.getArea() > bestSpace.getArea())
				bestSpace = space;
		}
		return bestSpace;
	}
	@Override
	public Space[] getSortedSpaces() {
		BuildingNode node = headNode.getNext();
		if (node == null) return null;
		
		int spaceNumber = 0;
		int floorsCount = floorsCount();
		Space[] sortedSpaces = new Space[spacesCount()];
		
		for (int i = 0; i < floorsCount; i++) {
			Space[] spaces = node.getFloor().getSpaces();
			for (int j = 0; j < spaces.length; j++)
				sortedSpaces[spaceNumber++] = spaces[j];
			node = node.getNext();
		}
		
		for (int i = 0; i < sortedSpaces.length; i++)
			for (int j = i; j < sortedSpaces.length; j++)
				if (sortedSpaces[j].getArea() > sortedSpaces[i].getArea()) {
					Space tmp = sortedSpaces[j];
					sortedSpaces[j] = sortedSpaces[i];
					sortedSpaces[i] = tmp;
				}
		return sortedSpaces;
	}
	
	private class BuildingIterator implements Iterator<Floor> {
		BuildingNode node;
		int index;
		int floorsCount;
		
		public BuildingIterator() {
			node = headNode;
			index = 0;
			floorsCount = floorsCount();
		}
		
		@Override
		public boolean hasNext() {
			return index < floorsCount;
		}

		@Override
		public Floor next() {
			index++;
			node = node.getNext();
			return node.getFloor();
		}

		@Override
		public void remove() {}
	}
	
	@Override
	public Iterator<Floor> iterator() {
		return new BuildingIterator();
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("OfficeBuilding (" + floorsCount());
		Iterator i = iterator();
		while (i.hasNext())
			s.append(", " + i.next().toString());
		return s.append(")").toString();
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this) 
			return true;
		if (object.getClass() != OfficeBuilding.class)
			return false;
		
		OfficeBuilding building = (OfficeBuilding) object;
		
		if (!(building.floorsCount() == this.floorsCount())) 
			return false;

		Iterator i = this.iterator();
		Iterator j = building.iterator();
		while (i.hasNext()) {
			Floor thisFloor  = (Floor) i.next();
			Floor buildingFloor = (Floor) j.next();
			if (!buildingFloor.equals(thisFloor))
				return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int hash = this.floorsCount();
		Iterator i = iterator();
		while (i.hasNext()) {
			hash ^= i.next().hashCode();
		}
		return 63 ^ hash;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		OfficeBuilding result = (OfficeBuilding) super.clone();
		result.headNode = new BuildingNode();
		Iterator i = iterator();
		int count = 0;
		while (i.hasNext()) {
			Floor floor = (Floor) i.next();
			BuildingNode node = new BuildingNode((Floor) floor.clone());
			result.addNode(node, count++);
		}
		return result;
	}
}
