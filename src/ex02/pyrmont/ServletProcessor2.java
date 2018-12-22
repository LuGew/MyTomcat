package ex02.pyrmont;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor2 {
    public void process(Request request, Response response) {
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader urlClassLoader = null;
        URL[] urls = new URL[1];
        URLStreamHandler urlStreamHandler = null;
        File classPath = new File(Constants.WEB_ROOT);
        try {
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            urls[0] = new URL(null, repository, urlStreamHandler);
            urlClassLoader = new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        Class myClass = null;
        try {
            myClass = urlClassLoader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }
        Servlet servlet = null;
        RequestFacade requestFacade = new RequestFacade(request);
        ResponseFacade responseFacade = new ResponseFacade(response);
        try {
            servlet = (Servlet) myClass.newInstance();
            servlet.service((ServletRequest) requestFacade, (ServletResponse) responseFacade);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.toString());
        } catch (Throwable e) {
            System.out.println(e.toString());
        }

    }
}
