package ds.serv.bubo.thread;

/**
 * VolaliteDemo
 *
 * @author Leslie
 * @since 2017/1/4
 */
public class VolaliteDemo {

    private static boolean ready = false;
    private static int number;

    public static class NoVisible extends Thread{
        @Override
        public void run() {
            while(!ready){
                System.out.println("not see......");

            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws Exception {
//        NoVisible nv = new NoVisible();
//        nv.start();
        for(int i = 0; i < 1000; ++i){
            new NoVisible().start();
            number = i;
            ready = true;
            Thread.sleep(1000 * 3);
            ready = false;
            Thread.sleep(1000 * 1);
        }
    }
}
