import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientConnectionManager {

    private InputStream clientRequestInput;
    private OutputStream clientResponseOutput;
    private RequestParser requestParser;
    private RequestServicer requestResponder;

    public ClientConnectionManager() {
        requestParser = new RequestParser(new Request());
        requestResponder = new RequestServicer();
    }

    public void handleRequest(Socket socket) {
        connect(socket);
        Request request = parseRequest();
        byte[] response = respondTo(request);
        write(response, clientResponseOutput);
    }

    private void connect(Socket socket) {
        try {
            clientRequestInput = socket.getInputStream();
            clientResponseOutput = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request parseRequest() {
        return requestParser.parseRequest(clientRequestInput);
    }

    private byte[] respondTo(Request request) {
        return requestResponder.respondTo(request);
    }

    private void write(byte[] response, OutputStream socketOut) {
        try {
            socketOut.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
