package ds.serv.bubo.net;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * FTPDemo
 *
 * @author Leslie
 * @since 2016/12/21
 */
public class FTPDemo {

    public static void main(String[] args) {
        FTPClient ftpClient = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();

        String text = "hello, world!";

        List<String> texts = Arrays.asList("hello, world!", "nihaoma?");

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : texts) {
            stringBuilder.append(s).append("\n");
        }



        try {
            InputStream input = new ByteArrayInputStream(stringBuilder.toString().getBytes("UTF-8"));

            ftpClient.setConnectTimeout(2000);
            ftpClient.connect("121.46.23.211");

            ftpClient.login("watsons", "XdV7JMM1");

            System.out.println(ftpClient.getReplyCode());

            ftpClient.changeWorkingDirectory("/logs");
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            ftpClient.storeFile("hello.ftp.txt", input);

            input.close();
            ftpClient.logout();
            ftpClient.disconnect();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
