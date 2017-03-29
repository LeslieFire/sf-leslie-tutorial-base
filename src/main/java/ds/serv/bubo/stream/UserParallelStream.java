package ds.serv.bubo.stream;

import java.util.Arrays;
import java.util.List;

/**
 * Author: Leslie
 * Date: 8/8/2016.
 */
public class UserParallelStream {

    public static void main(String[] args){
        List<Integer> a = Arrays.asList(1,2,3,4,5,6,7);
        System.out.println(
                a.parallelStream()
                    .mapToInt(i -> (int)i)
                    .filter(i -> i > 3)
                    .map(i -> i* i)
                    .distinct()
                    .limit(3)
                    .max()
        );
    }
}
