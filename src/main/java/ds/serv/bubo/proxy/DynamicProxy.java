package ds.serv.bubo.proxy;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author: Leslie
 * Date: 11/8/2016.
 */
public class DynamicProxy {

    public static void main(String[] args){
        RealSubject subject = new RealSubject();

        // 调研处理业务的具体实现
        InvocationHandler h = new MyInvocationHandler(subject);

        Class<?> cls = subject.getClass();

        Subject subjectProxy = (Subject) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), h);

        System.out.println(subjectProxy instanceof Proxy); // 首先,它是一个代理
        System.out.println("Class " + subjectProxy.getClass().toString());
        System.out.println("subject 中的属性有");
        Field[] fields = subjectProxy.getClass().getDeclaredFields();
        for(Field f : fields){
            System.out.printf(f.getName() + ", ");
        }

        System.out.println("\nsubject 中的方法有");
        Method[] methods = subjectProxy.getClass().getDeclaredMethods();
        for(Method m : methods){
            System.out.println(m.getName());
        }

        // 父类
        System.out.println("Super class : " + subjectProxy.getClass().getSuperclass());
        // 接口
        Class<?>[] interfaces = subjectProxy.getClass().getInterfaces();
        for(Class<?> i : interfaces){
            System.out.println(i.getName());
        }

        System.out.println("Output: ");
        subjectProxy.request();
//        File file = new File("proxy1.class");
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(file);
//            ObjectOutputStream oos = null;
//            try{
//                oos = new ObjectOutputStream(fos);
//                oos.writeObject(subjectProxy);
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

interface Subject {
    public void request();
}

class RealSubject implements Subject, Serializable{

    private String m;
    @Override
    public void request() {
        try{
            Thread.sleep(3000);
            System.out.println("I'm the King");
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

class MyInvocationHandler implements InvocationHandler, Serializable{

    private Object subject;

    public MyInvocationHandler() {
    }

    public MyInvocationHandler(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before analyze");
        Object oc = method.invoke(subject, args);
        System.out.println("After analyze");

        return oc;
    }
}
