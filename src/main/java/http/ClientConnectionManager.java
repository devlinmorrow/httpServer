package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientConnectionManager {

    private InputStream clientInput;
    private OutputStream clientOutput;
    private RequestParser requestParser;
    private RequestResponder requestResponder;
    private ResponseWriter responseWriter;

    public ClientConnectionManager() {
        requestParser = new RequestParser();
        requestResponder = new RequestResponder();
        responseWriter = new ResponseWriter();
    }

    public void respondTo(Socket clientConnection) {
        connectInAndOut(clientConnection);
        Request request = requestParser.parse(clientInput);
        Response response = requestResponder.respondTo(request);
        responseWriter.write(response, clientOutput);
    }

    private void connectInAndOut(Socket clientConnection) {
        try {
            clientInput = clientConnection.getInputStream();
            clientOutput = clientConnection.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
