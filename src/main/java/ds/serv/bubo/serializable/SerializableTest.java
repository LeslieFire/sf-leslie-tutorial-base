package ds.serv.bubo.serializable;

import java.io.*;

/**
 * Author: Leslie
 * Date: 10/8/2016.
 */
public class SerializableTest {

    public static void main(String[] args){

        File file = new File("serialable.txt");

        FileInputStream fis = null;
        try{
            fis = new FileInputStream(file);
            ObjectInputStream ois = null;
            try{
                ois = new ObjectInputStream(fis);
                Person person = (Person) ois.readObject();
                System.out.println(person);

            }catch (IOException e){
                e.printStackTrace();
            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }
            finally {
                try{
                    ois.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            try{
                fis.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(file);
//            ObjectOutputStream oos = null;
//            try{
//                oos = new ObjectOutputStream(fos);
//                Person person = new Person("nick", 99);
//                System.out.println(person);
//                oos.writeObject(person);
//                oos.flush();
//            }catch(IOException e){
//                e.printStackTrace();
//            }finally {
//                try {
//                    oos.close();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//            }
//
//        }catch (FileNotFoundException e){
//            System.out.println(e.getMessage());
//        }finally {
//            try {
//                fos.close();
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        }





    }
}

class Person implements Serializable{
    private static final long serialVersionUID = 1L;

    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString(){
        return "name:" + name + "\tage:" + age;
    }
}