package buildings.office;

import java.io.Serializable;
import java.util.Iterator;

import buildings.dwelling.DwellingFloor;
import interfaces.Floor;
import interfaces.Space;
import exceptions.SpaceIndexOutOfBoundsException;

class FloorNode implements Serializable {
	Space space;
	FloorNode next;
	
	public FloorNode() { }
	public FloorNode(Space space) {
		this.space = space;
	}
	
	public Space getSpace() {
		return space;
	}
	public void setSpace(Space space) {
		this.space = space;
	}
	
	public FloorNode getNext() {
		return next;
	}
	public void setNext(FloorNode next) {
		this.next = next;
	}
	
//	@Override
//	public Object clone() throws CloneNotSupportedException {
//		FloorNode result = (FloorNode) super.clone();
//		result.space = (Space) space.clone();
//		result.next  = (FloorNode) next.clone();
//		return result;
//	}
}

public class OfficeFloor implements Floor, Serializable {
	FloorNode headNode;
	
	private void addNode(FloorNode node, int number) {
		if (number == 0) {
			if (spacesCount() != 0)
				node.setNext(headNode.getNext());
			else
				node.setNext(node);
			headNode.setNext(node);
		}
		else {
			FloorNode current = getNode(number - 1);
			node.setNext(current.getNext());
			current.setNext(node);
		}
	}
	private void removeNode(int number) {
		FloorNode rmNode;
		FloorNode node;
		
		if (number == 0) {
			rmNode = headNode.getNext();
			if (spacesCount() != 0) {
				node = getNode(spacesCount() - 1);
				node.setNext(rmNode.getNext());
				headNode.setNext(rmNode.getNext());
			}
			else
				headNode.setNext(null);
		}
		else {
			node = getNode(number - 1);
			rmNode = node.getNext();
			node.setNext(rmNode.getNext());
		}
		rmNode = null;
	}
	private FloorNode getNode(int number) {
		FloorNode node = headNode.getNext();
		if (node == null) return null;
		for (int i = 0; i != number; i++)
			node = node.getNext();
			
		return node;
	}
	
	public OfficeFloor(int officesCount) {
		headNode = new FloorNode();		
		int count = 0;
		while (officesCount != spacesCount())
			insertSpace(new Office(), count++);
	}
	
	public OfficeFloor(Space[] offices) {
		headNode = new FloorNode();		
		for (int i = 0; i < offices.length; i++)
			insertSpace(offices[i], i);
	}
	
	@Override
	public int spacesCount() {
		FloorNode node = headNode.getNext();
		if (node == null) return 0;
		int total = 1;	
		for (node = node.getNext(); node != headNode.getNext(); node = node.getNext())
			total++;
		return total;
	}
	
	@Override
	public int roomsCount() {
		FloorNode node = headNode.getNext();
		if (node == null) return 0;		
		int total = node.getSpace().getRoomsCount();
		for (node = node.getNext(); node != headNode.getNext(); node = node.getNext())
			total += node.getSpace().getRoomsCount();
		return total;
	}	
	@Override
	public float totalArea() {
		FloorNode node = headNode.getNext();
		if (node == null) return 0;		
		float total = node.getSpace().getArea();
		for (node = node.getNext(); node != headNode.getNext(); node = node.getNext())
			total += node.getSpace().getArea();
		return total;
	}
	
	@Override
	public Space[] getSpaces() {
		FloorNode node = headNode.getNext();
		Space[] offices = new Office[spacesCount()];
		for (int i = 0; i < spacesCount(); i++) {
			offices[i] = node.getSpace();
			node = node.getNext();
		}
		return offices;
	}
	
	public int tSpacesCount() {
		Iterator i = iterator();
		int count = 0;
		while (i.hasNext()) {
			Space space = (Space) i.next();
			if (space.getRoomsCount() == 2)
				count++;
		}
		return count;
	}
	
	@Override
	public Space getSpace(int number) throws SpaceIndexOutOfBoundsException {
		if (number < 0 || number >= spacesCount())
			throw new SpaceIndexOutOfBoundsException("Space with number " + number + " does not exist");
		return getNode(number).getSpace();
	}
	@Override
	public void removeSpace(int number) throws SpaceIndexOutOfBoundsException {
		if (number < 0 || number >= spacesCount())
			throw new SpaceIndexOutOfBoundsException("Space with number " + number + " can not be removed");
		removeNode(number);
	}
	@Override
	public void changeSpace(Space office, int number) throws SpaceIndexOutOfBoundsException {
		if (number < 0 || number >= spacesCount())
			throw new SpaceIndexOutOfBoundsException("Space with number " + number + " does not exist");
		getNode(number).setSpace(office);
	}
	@Override
	public void insertSpace(Space office, int number) throws SpaceIndexOutOfBoundsException {
		if (number < 0 || number > spacesCount())
			throw new SpaceIndexOutOfBoundsException("Space with number " + number + " can not be inserted");
		addNode(new FloorNode(office), number);
	}
	
	@Override
	public Space getBestSpace() {
		FloorNode node = headNode.getNext();
		if (node == null) return null;
		Space bestSpace = node.getSpace();	
		for (node = node.getNext(); node != headNode.getNext(); node = node.getNext())
			if (node.getSpace().getArea() > bestSpace.getArea())
				bestSpace = node.getSpace();
		return bestSpace;
	}
	
	private class FloorIterator implements Iterator {
		FloorNode node;
		int index;
		int spacesCount;
		
		public FloorIterator() {
			node = headNode;
			index = 0;
			spacesCount = spacesCount();
		}
		
		@Override
		public boolean hasNext() {
			return index < spacesCount;
		}

		@Override
		public Object next() {
			index++;
			node = node.getNext();
			return node.getSpace();
		}

		@Override
		public void remove() {}
	}
	
	@Override
	public Iterator iterator() {
		return new FloorIterator();
	}
	
	@Override
	public String toString() {		
		StringBuilder s = new StringBuilder("OfficeFloor (" + spacesCount());
		Iterator i = iterator();
		while (i.hasNext()) 
			s.append(", " + i.next().toString());
		return s.append(")").toString();
	}
	
	@Override 
	public boolean equals(Object object) {
		if (object == this) 
			return true;
		if (object.getClass() == OfficeFloor.class)
			return false;
		
		OfficeFloor floor = (OfficeFloor) object;
		if (!(floor.spacesCount() == this.spacesCount())) 
			return false;

		Iterator i = this.iterator();
		Iterator j = floor.iterator();
		while (i.hasNext()) {
			Space thisSpace  = (Space) i.next();
			Space floorSpace = (Space) j.next();
			if (!floorSpace.equals(thisSpace))
				return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int hash = this.spacesCount();
		Iterator i = iterator();
		while (i.hasNext()) {
			hash ^= i.next().hashCode();
		}
		return 63 ^ hash;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		OfficeFloor result = (OfficeFloor) super.clone();
		result.headNode = new FloorNode();
		Iterator i = iterator();
		int count = 0;
		while (i.hasNext()) {
			Space space = (Space) i.next();
			FloorNode node = new FloorNode((Space) space.clone());
			result.addNode(node, count++);
		}
		return result;
	}
}
