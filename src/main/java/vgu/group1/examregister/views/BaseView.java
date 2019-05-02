package vgu.group1.examregister.views;

import vgu.group1.examregister.Config;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;

abstract public class BaseView {

    @Context
    HttpServletRequest request;

    private static final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public String getUserName() {
        return (String) request.getAttribute("username");
    }

    public String getUserRole() {
        return (String) request.getAttribute("role");
    }

    public int getAccountId() {
        return (Integer) request.getAttribute("accountId");
    }

    protected static String getHTMLFile(String fileName) throws IOException {
        String template = readHTMLFileContent(Config.HTML_BASE_FILE);
        String content = readHTMLFileContent(fileName);
        return template.replace(Config.HTML_CONTENT_MARKER, content);
    }

    private static String readHTMLFileContent(String fileName) throws IOException {
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

    protected static Time toSQLTime(String timeStr) {
        return Time.valueOf(timeStr + ":0");
    }
}
