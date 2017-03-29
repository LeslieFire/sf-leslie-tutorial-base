package ds.serv.bubo.thread;


/**
 * Author: Leslie
 * Date: 7/8/2016.
 */
public class ThreadPractice {

    public static void main(String[] args){
        Thread thread = new Thread(new Thread2());
        Thread thread1 = new Thread1();

        thread.start();
        thread1.start();
        new Thread(){
            public void run(){
                String[] arr = "abc".split("");
                for(String x : arr){
                    System.out.println(x);
                }

            }

        }.start();

        System.out.println("start thread");
        try {
            thread.join();

        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("finish");
    }
}
