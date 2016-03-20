package buildings.threads;

import java.util.Iterator;

public class SequentalCleaner implements Runnable {

    FloorSynchronizer floor;

    public SequentalCleaner(FloorSynchronizer floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        try {
            Iterator i = floor.getFloor().iterator();
            while (i.hasNext()) {
                i.next();
                floor.clean();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
