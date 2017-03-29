package ds.serv.bubo.thread;

/**
 * Author: Leslie
 * Date: 7/8/2016.
 */
public class Thread2 implements Runnable {
    public void run() {
        for (int i = 0; i < 100; ++i){
            System.out.println(i);
        }
    }
}
