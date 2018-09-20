package http.server;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter {

    public void writeToSocket(byte[] response, OutputStream clientResponseOutput) throws IOException {
        clientResponseOutput.write(response);
    }
}
