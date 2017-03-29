package ds.serv.bubo.generic;

import java.util.ArrayList;

/**
 * Author: Leslie
 * Date: 12/8/2016.
 */
public class GenericTreeClass {

    public static void main(String[] args){

        TNode<String> t  = new TNode<>("root");
        t.add("left");
        t.add("middle");
        t.add("jiahui");
        t.getChild(0).add("summer");
        t.getChild(0).add("winner");
        t.Travese();
    }
}

class TNode<T>{
    private T value;
    private ArrayList<TNode<T>> children = new ArrayList<>();

    TNode(T value){
        this.value = value;
    }
    public T getValue(){
        return this.value;
    }

    public void add(T v){
        TNode<T> child = new TNode<T>(v);
        children.add(child);
    }

    public TNode<T> getChild(int i){
        if (i < 0 || i >= children.size()) return null;
        return (TNode<T>) this.children.get(i);
    }

    public void Travese(){
        System.out.println(this.value);
        for(TNode<T> child : children){
            child.Travese();
        }
    }

}
