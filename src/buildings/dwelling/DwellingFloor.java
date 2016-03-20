package buildings.dwelling;

import java.io.Serializable;
import java.util.Iterator;

import interfaces.Floor;
import interfaces.Space;
import exceptions.SpaceIndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DwellingFloor implements Floor, Serializable, Cloneable {

    private List<Space> spaces;

    public DwellingFloor(int spacesCount) {
        
        spaces = new ArrayList<>();
        for (int i = 0; i < spaces.size(); i++) {
            spaces.add(new Flat());
        }
    }

    public DwellingFloor(Space[] spaces) {
       
        this.spaces = new ArrayList<>();
        this.spaces.addAll(Arrays.asList(spaces));
    }

    @Override
    public int spacesCount() {
        return spaces.size();
    }

    @Override
    public int roomsCount() {
        int total = 0;
        for (Space space : spaces) {
            total += space.getRoomsCount();
        }
        return total;
    }

    @Override
    public float totalArea() {
        float total = 0;
        for (Space space : spaces) {
            total += space.getArea();
        }
        return total;
    }

    @Override
    public Space[] getSpaces() {
        return (Space[]) spaces.toArray();
    }

    @Override
    public Space getSpace(int number) throws SpaceIndexOutOfBoundsException {
        if (number < 0 || number >= spaces.size()) {
            throw new SpaceIndexOutOfBoundsException("space with number " + number + "does not exist");
        }
        return spaces.get(number);
    }

    @Override
    public void changeSpace(Space space, int number) throws SpaceIndexOutOfBoundsException {
        if (number < 0 || number >= spaces.size()) {
            throw new SpaceIndexOutOfBoundsException("space with number " + number + "does not exist");
        }
        this.spaces.set(number, space);
    }

    @Override
    public void insertSpace(Space space, int number) throws SpaceIndexOutOfBoundsException {
        if (number < 0 || number > spaces.size()) {
            throw new SpaceIndexOutOfBoundsException("space with number " + number + " can not be inserted");
        }
        spaces.add(number, space);
//        Space[] newSpaces = new Flat[this.spaces.size() + 1];
//
//        for (int i = 0; i < number; i++) {
//            newSpaces[i] = spaces[i];
//        }
//        for (int i = number + 1; i < newSpaces.length; i++) {
//            newSpaces[i] = spaces[i];
//        }
//        newSpaces[number] = space;
//
//        spaces = newSpaces;
    }

    @Override
    public void removeSpace(int number) throws SpaceIndexOutOfBoundsException {
        if (number < 0 || number >= spaces.size()) {
            throw new SpaceIndexOutOfBoundsException("Space with number " + number + " can not be removed");
        }
        Space[] newSpaces = new Flat[spaces.size() - 1];
        spaces.remove(number);
//        for (int i = 0; i < number; i++) {
//            newSpaces[i] = spaces[i];
//        }
//        for (int i = number + 1; i < spaces.length; i++) {
//            newSpaces[i - 1] = spaces[i];
//        }
//
//        spaces = newSpaces;
    }

    @Override
    public Space getBestSpace() {
        Space bestSpace = spaces.get(0);
        for (int i = 1; i < spaces.size(); i++) {
            if (spaces.get(i).getArea() > bestSpace.getArea()) {
                bestSpace = spaces.get(i);
            }
        }
        return bestSpace;
    }

    public void setSpaces(Space[] spaces) {
        this.spaces.addAll(Arrays.asList(spaces));
    }

    public int getSpacesCount() {
        return spaces.size();
    }

    private class FloorIterator implements Iterator {

        int index;

        public FloorIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < spaces.size();
        }

        @Override
        public Object next() {
            return spaces.get(index++);
        }

        @Override
        public void remove() {
        }

    }

    @Override
    public Iterator<Floor> iterator() {
        return new FloorIterator();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("DwellingFloor (" + spaces.size());
        for (Space space : spaces) {
            s.append(", ").append(space.toString());
        }
        return s.append(")").toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object.getClass() != DwellingFloor.class) {
            return false;
        }

        DwellingFloor floor = (DwellingFloor) object;

        if (!(floor.spacesCount() == spaces.size())) {
            return false;
        }
        for (int i = 0; i < spaces.size(); i++) {
            if (!floor.getSpace(i).equals(spaces.get(i))) {
                return false;
            }
        }
        return true;
    }

    /*@Override
     public int hashCode() {
     int hash = spaces.length;
     for (Space space : spaces) {
     hash ^= space.hashCode();
     }
     return hash ^ 22;
     }*/
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Arrays.deepHashCode(this.spaces.toArray());
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DwellingFloor result = (DwellingFloor) super.clone();
        for (int i = 0; i < spaces.size(); i++) {
            result.changeSpace((Space) spaces.get(i).clone(), i);
        }
        return result;
    }
}
