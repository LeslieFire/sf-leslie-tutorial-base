package ds.serv.bubo.io;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * CsvReaderDemo
 *
 * @author Leslie
 * @since 2017/1/9
 */
public class CsvReaderDemo {
    public static void main(String[] args) {

        try {
            CSVReader csvReader = new CSVReader(new FileReader("pinzhi_sample2.csv"), ',', '"');

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
