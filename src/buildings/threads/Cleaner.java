package buildings.threads;

import java.util.Iterator;

import interfaces.Floor;
import interfaces.Space;

public class Cleaner extends Thread {

    Floor floor;

    public Cleaner(Floor floor) {
        this.floor = floor;
    }

    public void run() {
        Iterator i = floor.iterator();
        int count = 0;
        while (i.hasNext()) {
            Space space = (Space) i.next();
            System.out.println(String.format("Cleaning room number %d with total area %d square meters",
                    count++, space.getRoomsCount()));
        }
    }
}
