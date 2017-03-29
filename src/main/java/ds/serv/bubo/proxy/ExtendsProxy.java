package ds.serv.bubo.proxy;

/**
 * Author: Leslie
 * Date: 11/8/2016.
 */
public class ExtendsProxy {

    public static void main(String[] args){
        Analyze ana = new Proxy1();
        ana.doReport();

        Analyze ana2  = new Analyzer();
        ana2.doReport();
    }
}

interface Analyze{

    void doReport();
}

class Analyzer implements Analyze{

    @Override
    public void doReport() {
        System.out.println("Question, data wraggling, data clean , data analyse, visualization, report");
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class Proxy1 extends Analyzer{

    @Override
    public void doReport(){
        long startTime = System.currentTimeMillis();
        super.doReport();
        long endTime = System.currentTimeMillis();
        System.out.println("Time cost of one report : " + (endTime - startTime) + " ms");
    }
}


