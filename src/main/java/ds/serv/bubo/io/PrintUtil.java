package ds.serv.bubo.io;

import java.io.*;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * PrintUtil
 *
 * @author Leslie
 * @since 2017/1/9
 */
public class PrintUtil {

    public static List<?> row(Object... values)
    {
        return asList(values);
    }

    public static List<List<?>> rows(List<?>... rows)
    {
        return asList(rows);
    }

    public static boolean write2Csv(List<String> fieldNames, List<List<?>> outputLines, Writer writer){
        try {
            OutputPrinter printer = new CsvPrinter(fieldNames, writer, true);
            printer.printRows(outputLines, true);
            printer.finish();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean write2Csv(List<String> fieldNames, List<List<?>> outputLines, String filename){
        try{
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename), "utf-8");
            write2Csv(fieldNames, outputLines, writer);
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
