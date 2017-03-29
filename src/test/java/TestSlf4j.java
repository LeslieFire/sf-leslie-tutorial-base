import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * Author: Leslie
 * Date: 5/8/2016.
 */
public class TestSlf4j {

    @Test
    public void testSlf4j(){
        Log log = LogFactory.getLog(TestSlf4j.class);

        log.info("kouki");

    }
}
