package ex05.pyrmont.core;

import org.apache.catalina.*;

import javax.servlet.http.HttpServletRequest;

public class SimpleContextMapper implements Mapper {
    private SimpleContext context = null;


    public Container getContainer() {
        return context;
    }

    public void setContainer(Container container) {
        if (!(container instanceof example.ex05.pyrmont.core.SimpleContext))
            throw new IllegalArgumentException
                    ("Illegal type of container");
        context = (SimpleContext) container;
    }

    public String getProtocol() {
        return null;
    }

    public void setProtocol(String protocol) {

    }

    public Container map(Request request, boolean update) {
        // Identify the context-relative URI to be mapped
        String contextPath =
                ((HttpServletRequest) request.getRequest()).getContextPath();
        String requestURI = ((HttpRequest) request).getDecodedRequestURI();
        String relativeURI = requestURI.substring(contextPath.length());
        // Apply the standard request URI mapping rules from the specification
        Wrapper wrapper = null;
        String servletPath = relativeURI;
        String pathInfo = null;
        String name = context.findServletMapping(relativeURI);
        if (name != null)
            wrapper = (Wrapper) context.findChild(name);
        return (wrapper);
    }
}
