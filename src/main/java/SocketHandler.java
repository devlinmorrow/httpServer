import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class SocketHandler {

    private MyInputReader myInputReader;
    private MyOutputWriter myOutputWriter;
    private GETHandler getHandler;

    public SocketHandler() {
        myInputReader = new MyInputReader();
        myOutputWriter = new MyOutputWriter();
        getHandler = new GETHandler();
    }

    public void handleRequest(Socket socket) {
        try {
            String request = readRequest(socket.getInputStream());
            String response = findResponse(request);
            writeResponse(socket.getOutputStream(), response);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readRequest(InputStream socketIn) {
        return myInputReader.readInput(socketIn);
    }

    private String findResponse(String request) {
        return getHandler.handleGET(request);
    }

    private void writeResponse(OutputStream socketOut, String response) {
        String fullResponse = "HTTP/1.1 200 OK\nContent-Type: text/plain" +
                "\nContent-Length: " + response.length() + "\n\n" + response;
        System.out.println(fullResponse.length());
        myOutputWriter.write(socketOut, fullResponse);
    }
}
