package ex03.pyrmont.connector.http;

import javax.servlet.ServletInputStream;
import java.io.IOException;

public class RequestStream extends ServletInputStream {
    @Override
    public int read() throws IOException {
        return 0;
    }
}
