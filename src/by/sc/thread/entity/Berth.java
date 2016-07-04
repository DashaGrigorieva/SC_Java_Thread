package by.sc.thread.entity;

import org.apache.log4j.Logger;

/**
 * Created by User on 20.06.2016.
 */

public class Berth {

    private static final Logger LOG = Logger.getLogger(Berth.class);

    private int berthId;

    public Berth(int id) {
        super();
        berthId = id;
        LOG.info("Berth #" + berthId + " is created");
    }

    public int getBerthId() {
        return berthId;
    }
    public void setBerthId(int id) {
        berthId = id;
    }


}
