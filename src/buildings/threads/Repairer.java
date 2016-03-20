package buildings.threads;

import java.util.Iterator;

import interfaces.Floor;
import interfaces.Space;

public class Repairer extends Thread {

    Floor floor;

    public Repairer(Floor floor) {
        this.floor = floor;
    }

    public void run() {
        Iterator i = floor.iterator();
        int count = 0;
        while (i.hasNext()) {
            Space space = (Space) i.next();
            System.out.println(String.format("Repairing space number %d with total area %f square meters", count++, space.getArea()));
        }
    }
}
