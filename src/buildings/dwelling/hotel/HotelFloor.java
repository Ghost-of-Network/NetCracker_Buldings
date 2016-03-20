package buildings.dwelling.hotel;

import buildings.dwelling.DwellingFloor;
import exceptions.InvalidStarsCountException;

import interfaces.Space;
import java.io.Serializable;

public class HotelFloor extends DwellingFloor implements Serializable  {

    static final int DEFAULT_STARS = 1;

    private byte stars;

    public HotelFloor(int spacesCount) {
        super(spacesCount);
        stars = DEFAULT_STARS;
    }

    public HotelFloor(Space[] spaces) {
        super(spaces);
        stars = DEFAULT_STARS;
    }

    /* private void defaultStars(int spacesCount) {
     for (int i = 0; i < spacesCount; i++) {
     starsCount[i] = defaultStarsCount;
     }
     }*/

    public void setStars(byte starCount) throws InvalidStarsCountException {
        if (starCount < 0 || starCount > 5) {
            throw new InvalidStarsCountException("Stars count can not be less than 1 and more than 5");
        }
        this.stars = starCount;
    }

    public byte getStars() {
        return this.stars;
    }

    /*   public byte getStarsCount(int number){
     return this.starsCount[number];
     }*/
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("HotelFloor" + super.spacesCount());

        for (Space space : super.getSpaces()) {
            s.append(", ").append(space.toString());
        }
        return s.append(")").toString();
    }

    @Override
    public boolean equals(Object object) {        
        Space[] hotelSpace = super.getSpaces();
        if (object == this) {
            return true;
        }
        if (object.getClass() != DwellingFloor.class) {
            return false;
        }

        DwellingFloor floor = (DwellingFloor) object;

        if (!(floor.getSpacesCount() == hotelSpace.length)) {
            return false;
        }
        for (int i = 0; i < hotelSpace.length; i++) {
            if (!floor.getSpace(i).equals(hotelSpace[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        Space[] hotelSpace = super.getSpaces();
        int hash = hotelSpace.length;
        for (Space space : hotelSpace) {
            hash ^= space.hashCode();
        }
        return hash ^ 23;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Space[] hotelSpaces = super.getSpaces();
        DwellingFloor result = (DwellingFloor) super.clone();
        result.setSpaces(hotelSpaces.clone());
        for (int i = 0; i < hotelSpaces.length; i++) {
            result.changeSpace((Space) hotelSpaces[i].clone(), i);
        }
        
        HotelFloor clonedFloor = new HotelFloor(hotelSpaces);
        clonedFloor.setStars(stars);
        
        return result;
    }
}
