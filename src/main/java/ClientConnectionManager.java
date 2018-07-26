import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientConnectionManager {

    private RequestParser requestParser;
    private MyOutputWriter myOutputWriter;
    private RequestServicer requestServicer;

    public ClientConnectionManager() {
        requestParser = new RequestParser(new Request());
        myOutputWriter = new MyOutputWriter();
        requestServicer = new RequestServicer();
    }

    public void handleRequest(Socket socket) {
        try {
            Request request = parseRequest(socket.getInputStream());
            String response = serviceRequest(request);
            writeResponse(socket.getOutputStream(), response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String serviceRequest(Request request) {
        return requestServicer.serviceRequest(request);
    }

    private Request parseRequest(InputStream clientRequestIn) {
        return requestParser.parseRequest(clientRequestIn);
    }

    private void writeResponse(OutputStream socketOut, String response) {
        myOutputWriter.write(socketOut, response);
    }
}
