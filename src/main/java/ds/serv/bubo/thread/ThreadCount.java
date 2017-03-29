package ds.serv.bubo.thread;

/**
 * Author: Leslie
 * Date: 7/8/2016.
 */
public class ThreadCount {
    static int cnt = 0;

    public static void main(String[] agrs){
        final int NUM = 10000;
        Thread[] threads = new Thread[NUM];

        for(int i = 0; i < NUM; ++i){
            threads[i] = new Thread(){
                @Override
                public void run(){
                    cnt ++;
                    try{
                        Thread.sleep(1);
                    }catch (InterruptedException ex){}

                }
            };
        }
        for(int i = 0; i < NUM; ++i){
            threads[i].start();
        }

//        try{
//            Thread.sleep(300);
//        }catch (InterruptedException ex){}

        System.out.printf("%d, %b\n", cnt, cnt == NUM);

    }
}
