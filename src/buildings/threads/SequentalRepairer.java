package buildings.threads;

import java.util.Iterator;

public class SequentalRepairer implements Runnable {

    FloorSynchronizer floor;

    public SequentalRepairer(FloorSynchronizer floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        try {
            Iterator i = floor.getFloor().iterator();
            while (i.hasNext()) {
                i.next();
                floor.repair();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
