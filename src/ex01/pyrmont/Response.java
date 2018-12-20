package ex01.pyrmont;


import java.io.*;

public class Response {
    public static final int BUFFER_SIZE = 2048;
    Request request;
    OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fileInputStream = null;
        try {
            File file = new File(HttpServer.WEB_ROOT, request.getUri());

            if (file.exists()) {

                fileInputStream = new FileInputStream(file);
                int ch = fileInputStream.read(bytes, 0, BUFFER_SIZE);
                while (ch != -1) {
                    outputStream.write(bytes, 0, ch);
                    ch = fileInputStream.read(bytes, 0, BUFFER_SIZE);
                }


            } else {
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type:text/html\r\n" +
                        "Content-Length:23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";

                outputStream.write(errorMessage.getBytes());

            }
        } catch (IOException e) {
            System.out.println(e.toString());

        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
