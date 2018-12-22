package ex02.pyrmont;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer2 {
    public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private boolean shutdown = false;
    private int port = 8080;

    public void await() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!shutdown) {
            try {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                Request request = new Request(inputStream);
                request.parse();
                Response response = new Response(outputStream);
                response.setRequest(request);
                if (request.getUri().startsWith("/servlet/")) {
                    ServletProcessor2 servletProcessor2 = new ServletProcessor2();
                    servletProcessor2.process(request, response);
                } else {
                    StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                    staticResourceProcessor.process(request, response);
                }

                socket.close();
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }

        }

    }

    public static void main(String[] args) {
        HttpServer2 httpServer1 = new HttpServer2();
        httpServer1.await();
    }
}
