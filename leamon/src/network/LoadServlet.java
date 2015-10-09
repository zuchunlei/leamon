package network;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        URL url = new URL("http://baidu.com");
        URL[] urls = new URL[] { url };
        ClassLoader loader = new URLClassLoader(urls);
        System.out.println(LoadServlet.class.getClassLoader());
        System.out.println(loader.getParent());
    }
}
