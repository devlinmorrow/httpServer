import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler {

    private RequestParser requestParser;
    private MyOutputWriter myOutputWriter;
    private GETHandler getHandler;

    public RequestHandler() {
        requestParser = new RequestParser();
        myOutputWriter = new MyOutputWriter();
        getHandler = new GETHandler();
    }

    public void handleRequest(Socket socket) {
        try {
            String response = constructResponse(socket.getInputStream());
            writeResponse(socket.getOutputStream(), response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String constructResponse(InputStream clientRequestIn) {
        String URI = findURI(clientRequestIn);
        return constructResponse(URI);
    }

    private String findURI(InputStream socketIn) {
        return requestParser.getURI(socketIn);
    }

    private String constructResponse(String request) {
        return getHandler.handleGET(request);
    }

    private void writeResponse(OutputStream socketOut, String response) {
        String fullResponse = "HTTP/1.1 200 OK\n\n" + response;
        myOutputWriter.write(socketOut, fullResponse);
    }
}
