package ex03.pyrmont.connector.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpProcessor {
    HttpConnector httpConnector;
    Request request;
    Response response;

    public HttpProcessor(HttpConnector httpConnector) {
        this.httpConnector = httpConnector;
    }

    public void process(Socket socket) {
        SocketInputStream socketInputStream = null;
        OutputStream outputStream = null;
        try {
            socketInputStream = new SocketInputStream(socket.getInputStream(), 2048);
            outputStream = socket.getOutputStream();
            request = new HttpRequest(socketInputStream);
            response = new HttpResponse(outputStream);
            response.setRequest(request);
            response.setHeader("Server", "Pyrmont Servlet Container");
            parseRequest(socketInputStream, outputStream);
            parseHeader(socketInputStream);
            if (request.getRequestUri().startWith("/servlet/")) {
                ServletProcessor servletProcessor = new ServletProcessor();
                servletProcessor.process(request, response);
            } else {
                StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                staticResourceProcessor.process(request, response);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
