package ds.serv.bubo.thread;

import java.util.Date;

/**
 * Author: Leslie
 * Date: 7/8/2016.
 */
public class ThreadDaemon {

    public static void main(String[] args){

        Thread myThread = new MyThread();
        myThread.setDaemon(true);
        myThread.start();

        System.out.println("MAIN==");

        try {
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("FINISH");

    }


}

class MyThread extends Thread{

    @Override
    public void run(){
        for(int i = 0; i < 10; ++i){
            System.out.println("id:" + i + "\tTime: " + new Date());
            try{
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
