package by.sc.thread.entity;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by User on 21.06.2016.
 */
public class Container {

    private static final Logger LOG = Logger.getLogger(Container.class);

    public static final int DEFAULT_PORT_CAPACITY = 200;

    private int containerNumber;
    private int portCapacity;
    private Lock lock  = new ReentrantLock();

    public Container() {
        this.containerNumber = new Random().nextInt(DEFAULT_PORT_CAPACITY);
        portCapacity = DEFAULT_PORT_CAPACITY;
    }
    public Container(int portCapacity) {
        this.portCapacity = portCapacity;
        this.containerNumber = new Random().nextInt(portCapacity);
    }
    public Container(int portCapacity, int containerNumber) {
        this.portCapacity = portCapacity;
        this.containerNumber = containerNumber;
    }

    public int getContainerNumber() { return containerNumber; }
    public void setContainerNumber(int containerNumber) { this.containerNumber = containerNumber; }

    public boolean takeContainer(int takeAmount) {
        if(containerNumber >= takeAmount) {
            try {
                lock.lock();
                containerNumber -= takeAmount;
            }
            finally {
                lock.unlock();
            }
            return true;
        }
        return false;
    }

    public boolean putContainer(int putAmount) {
        if(containerNumber + putAmount <= portCapacity) {
            try {
                lock.lock();
                containerNumber += putAmount;
            }
            finally {
                lock.unlock();
            }
            return true;
        }
        return false;
    }

}
