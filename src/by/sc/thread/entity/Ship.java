package by.sc.thread.entity;

import by.sc.thread.exception.ResourceException;
import org.apache.log4j.Logger;

import java.util.Random;

/**
 * Created by User on 20.06.2016.
 */
public class Ship extends Thread {

    private static final Logger LOG = Logger.getLogger(Ship.class);

    private static final int MAX_SHIP_CAPACITY = 30;

    private Port pool;
    private int shipId;
    private int capacity;
    private boolean status;

    public static Random random = new Random();

    public Ship (Port pool , int id) {
        this.pool = pool;
        shipId = id;
        capacity = random.nextInt(MAX_SHIP_CAPACITY) + 1;
        status = random.nextBoolean();
        LOG.info("Ship #" + shipId + " is created  capacity : " +  capacity + "  state : " + status);
    }

    public int getShipId() { return shipId; }
    public int getCapacity() { return capacity; }
    public boolean getStatus() { return this.status; }
    public void setShipId(int id) { this.shipId = id; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setStatus(boolean status) { this.status = status; }

    public void run() {
        Berth berth = null;
        try{
            berth = pool.getBerth(500);
            LOG.info("Ship #" + shipId + " took berth #" + berth.getBerthId());
            if (status) {
                unload();
            }
            else {
                load();
            }
        } catch (ResourceException e) {
            LOG.error("Ship #" + shipId + "  " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            LOG.info(e.getMessage());
        } finally{
            if(berth != null) {
                LOG.info("Ship #" + shipId + " released berth #" + berth.getBerthId());
                pool.returnBerth(berth);
            }
        }
    }

    public void unload() throws ResourceException, InterruptedException {
        for (int i = 0; i < 5; i++) {
            if (pool.unloadShip(capacity)) {
                LOG.info("Ship #" + shipId + " unloaded " + capacity + "  port_actual " + pool.getContainerNumber());
                break;
            }
            else {
                Thread.sleep(10);
                LOG.info("Ship #" + shipId + " is waiting : Port is overloaded");
            }
        }
    }

    public void load() throws ResourceException, InterruptedException {
        for (int i = 0; i < 5; i++) {
            if (pool.loadShip(capacity)) {
                LOG.info("Ship #" + shipId + " loaded " + capacity + "  port_actual " + pool.getContainerNumber());
                break;
            }
            else {
                Thread.sleep(10);
                LOG.info("Ship #" + shipId + " is waiting : No cargo in the port");
            }
        }
    }
}
