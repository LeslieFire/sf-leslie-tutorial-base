package ds.serv.bubo.thread;

/**
 * Author: Leslie
 * Date: 7/8/2016.
 */
public class Thread1 extends Thread {
    @Override
    public void run(){
        for(int i = -100; i < 0; ++i){
            System.out.println(i);
        }
    }
}
