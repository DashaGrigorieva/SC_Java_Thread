package by.sc.thread.entity;


import by.sc.thread.exception.ResourceException;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by User on 19.06.2016.
 */

public class Port {

    // порт организован как пул
    private static final Logger LOG = Logger.getLogger(Port.class);

    private static final int DEFAULT_BERTH_NUMBER = 3;

    private final Semaphore semaphore = new Semaphore(DEFAULT_BERTH_NUMBER, true);
    private final Queue<Berth> berths = new LinkedList<Berth>();
    private Container container;
    private Lock lock  = new ReentrantLock();

    public Port(int capacity) {
        for (int i = 0; i < DEFAULT_BERTH_NUMBER; i++) {
            this.berths.add(new Berth(i + 1));
        }
        container = new Container(capacity);
        LOG.info("Port is created  capacity : " + capacity + "  actual : " + container.getContainerNumber());
    }

    public Port(int capacity, int berthNumber) {
        for (int i = 0; i < berthNumber; i++) {
            this.berths.add(new Berth(i + 1));
        }
        container = new Container(capacity);
        LOG.info("Port is created  capacity : " + capacity + "  actual : " + container.getContainerNumber());
    }

    public Port() {
        for (int i = 0; i < DEFAULT_BERTH_NUMBER; i++) {
            this.berths.add(new Berth(i + 1));
        }
        container = new Container();
        LOG.info("Port is created  capacity : " + container.DEFAULT_PORT_CAPACITY + "  actual : " + container.getContainerNumber());
    }

    public Berth getBerth(long maxWaitMillis) throws ResourceException {
        try{
            if(semaphore.tryAcquire(maxWaitMillis, TimeUnit.MILLISECONDS)) {
                Berth berth = berths.poll();
                return berth;
            }
        } catch(InterruptedException e) {
            throw new ResourceException(e);
        }
        throw new ResourceException("Timeout");
    }

    public void returnBerth (Berth berth) {
        berths.add(berth);
        semaphore.release();
    }


    public int getContainerNumber() { return container.getContainerNumber(); }

    public boolean loadShip(int containerCount) throws ResourceException {
            return container.takeContainer(containerCount);
    }

    public boolean unloadShip(int containerCount) throws ResourceException {
            return container.putContainer(containerCount);
    }

}
