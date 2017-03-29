package ds.serv.bubo.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Author: Leslie
 * Date: 7/8/2016.
 */

class Producer implements Runnable{

    private BlockingQueue<Integer> queue;
    public Producer(BlockingQueue<Integer> queue){
        this.queue = queue;
    }

    public void run() {
        for (int i = 0; i < 10; ++i){
            try{
                Thread.sleep((int)Math.random() * 10);
                queue.put(i);
                System.out.println("Produce: " + i);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable{

    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    public void run() {
        for(int i = 0; i < 10; ++i){
            try{
                Thread.sleep((int)Math.random() * 10);
                Integer product = queue.take();
                System.out.println("Consume: " + product);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
public class BlockingQueueDemo {

    public static void main(String[] args){
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }
}
