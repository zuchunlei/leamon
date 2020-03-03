package leamon;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipDemo {

    public static void main(String[] args) throws IOException {
        ungzip();
    }

    public static void gzip() throws IOException {
        String filePath = "C:\\o.txt";
        String zipFilePath = "C:\\oz.txt";

        FileInputStream fis = new FileInputStream(filePath);

        // Ñ¹ËõÂß¼­
        FileOutputStream fos = new FileOutputStream(zipFilePath);
        GZIPOutputStream zipos = new GZIPOutputStream(fos);

        byte[] buffer = new byte[1024];
        int len = 0;

        while ((len = fis.read(buffer)) != -1) {
            zipos.write(buffer, 0, len);
        }

        zipos.finish();

        zipos.close();
        fis.close();
    }

    public static void ungzip() throws IOException {
        FileInputStream fis = new FileInputStream("C:\\oz.txt");
        GZIPInputStream gis = new GZIPInputStream(fis);

        FileOutputStream fos = new FileOutputStream("C:\\oo.txt");
        byte[] buffer = new byte[1024];
        int len = 0;

        while ((len = gis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }

        gis.close();
        fos.close();
    }
}
