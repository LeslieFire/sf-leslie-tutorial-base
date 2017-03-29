package ds.serv.bubo.thread;

/**
 * Author: Leslie
 * Date: 7/8/2016.
 */
public class SyncThread {
    Num num = new Num();

}

class Num{
    private int x = 0;
    private int y = 0;
    synchronized public void increase(){
        x++;
        y++;
    }
    synchronized public boolean testEquals(){
        boolean ok = (x == y);
        System.out.println("x = " + x + ", y = " + y + ", ok = " + ok);
        return ok;
    }
}

