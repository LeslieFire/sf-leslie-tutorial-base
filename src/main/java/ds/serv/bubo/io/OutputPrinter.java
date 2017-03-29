package ds.serv.bubo.io;

import java.io.IOException;
import java.util.List;

/**
 * OutputPrinter
 *
 * @author Leslie
 * @since 2017/1/9
 */
public interface OutputPrinter
{
    void printRows(List<List<?>> rows, boolean complete)
            throws IOException;

    void finish()
            throws IOException;
}
