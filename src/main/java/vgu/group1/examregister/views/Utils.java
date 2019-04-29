package vgu.group1.examregister.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class Utils {
    private static final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public static String getHTMLFile(String fileName) throws IOException {
        // open file
        URL url = classLoader.getResource("html/" + fileName);
        if (url == null)
            throw new FileNotFoundException("HTML file " + fileName + " is not found");
        File file = new File(url.getPath());
        FileInputStream fis = new FileInputStream(file);

        // read whole file
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        return new String(data);
    }
}
