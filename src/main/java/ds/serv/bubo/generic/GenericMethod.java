package ds.serv.bubo.generic;

import java.util.Date;

/**
 * Author: Leslie
 * Date: 13/8/2016.
 */
public class GenericMethod {

    public static void main(String[] args){
        Date date = BeanUtil.<Date>getInstance("java.util.Date");
        System.out.println(date);
    }
}

class BeanUtil{
    public static <T> T getInstance( String className){
        try{
            Class c = Class.forName( className );
            return (T) c.newInstance();
        }catch (ClassNotFoundException e){}
        catch (IllegalAccessException e){}
        catch (InstantiationException e){}
        return null;
    }
}


