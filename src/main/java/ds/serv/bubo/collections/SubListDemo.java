package ds.serv.bubo.collections;

import java.util.*;

/**
 * SubListDemo
 *
 * @author Leslie
 * @since 2016/12/21
 */
public class SubListDemo {
    public static void main(String[] args) {
        List<MyInteger> normal = new ArrayList<>();
        for(int i = 0; i < 10; ++i){
            normal.add(new MyInteger(i));
        }
        System.out.println(normal);
        int part = 5;
        for (int i = 0; i < part; i++) {
            convertSubList(normal.subList(i*2, i*2 + 2));
        }

        System.out.println(normal);
    }

    public static void convertSubList(List<MyInteger> ll){
        Iterator<MyInteger> iterator = ll.iterator();
        while(iterator.hasNext()){
            iterator.next().setIt(2);
        }
    }
}
class MyInteger {
    private Integer it;
    MyInteger(int x){
        this.it = x;
    }
    public void setIt(Integer it){
        this.it = it;
    }
    @Override
    public String toString() {
        return it.toString();
    }
}
