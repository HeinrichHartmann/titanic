package net.heinrich_hartmann.titanic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by hartmann on 2/9/14.
 */
public class EventTitanic {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public static void main(String[] args) {
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

            }
        }, 0, 1, TimeUnit.SECONDS);

    }
}
