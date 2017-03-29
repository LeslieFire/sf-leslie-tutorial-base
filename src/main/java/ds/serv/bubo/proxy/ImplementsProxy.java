package ds.serv.bubo.proxy;

/**
 * Author: Leslie
 * Date: 11/8/2016.
 */
public class ImplementsProxy {

    public static void main(String[] args){
        Cat cat = new Cat();
        CatProxy proxy = new CatProxy(cat);
        proxy.run();
    }
}

interface Animal{
    void run();
}

class Cat implements Animal{

    @Override
    public void run() {
        try{
            Thread.sleep(1000);
            System.out.println("run like a cat");
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
}

class CatProxy implements Animal{

    private Animal animal;

    public CatProxy(Animal cat) {
        this.animal = animal;
    }

    @Override
    public void run() {
        System.out.println("I'm a tiger!");
        animal.run();
        System.out.println("Finish");
    }
}
