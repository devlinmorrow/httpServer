import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler {

    private RequestParser requestParser;
    private MyOutputWriter myOutputWriter;
    private GETHandler getHandler;

    public RequestHandler() {
        requestParser = new RequestParser(new Request());
        myOutputWriter = new MyOutputWriter();
        getHandler = new GETHandler();
    }

    public void handleRequest(Socket socket) {
        try {
            Request request = assembleRequest(socket.getInputStream());
//            String response = askResponseFromHandler(request);
//            writeResponse(socket.getOutputStream(), response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request assembleRequest(InputStream clientRequestIn) {
        return requestParser.assembleRequest(clientRequestIn);
    }

    private void writeResponse(OutputStream socketOut, String response) {
        myOutputWriter.write(socketOut, response);
    }
}
