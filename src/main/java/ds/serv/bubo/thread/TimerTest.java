package ds.serv.bubo.thread;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: Leslie
 * Date: 7/8/2016.
 */
public class TimerTest {

    public static void main(String[] args){
        Timer timer = new Timer(true);
        TimerTask task = new MyTask2();
        timer.schedule(task, 1000, 1000);

        try {
            Thread.sleep(5000);
            //Thread.currentThread().join();
        }catch (InterruptedException e){}

    }
}

class MyTask2 extends TimerTask{

    int n = 0;
    public void run() {
        System.out.println("Time: " + new Date() + " -- " + n++);
    }
}
