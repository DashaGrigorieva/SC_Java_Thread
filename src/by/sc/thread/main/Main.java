package by.sc.thread.main;

import by.sc.thread.entity.Berth;
import by.sc.thread.entity.Port;
import by.sc.thread.entity.Ship;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by User on 17.06.2016.
 */
public class Main {

    public static void main(String[] args) {

        ArrayList<Ship> list = new ArrayList<>();

        Port port = new Port(200, 5 );

        for(int i = 0; i < 20; i++) {
            list.add(new Ship(port, i + 1));
        }
        for(Ship s : list) {
           s.start();
        }

/*        for(int i = 0; i < 20; i++) {
            new Ship(port, i + 1).start();
        }*/
    }

}
