package ds.serv.bubo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: Leslie
 * Date: 7/8/2016.
 */
public class ThreadPoolDemo {

    public static void main(String[] args){
        ExecutorService pool = Executors.newCachedThreadPool();
        MyTask t1 = new MyTask(10);
        MyTask t2 = new MyTask(1000);
        MyTask t3 = new MyTask(100);
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.shutdown();
    }
}


class MyTask implements Runnable{
    private int n;

    MyTask(int n){
        this.n = n;
    }

    public void run() {
        for(int i = 0; i < n; ++i){
            System.out.println("No. " + i);
        }
    }
}