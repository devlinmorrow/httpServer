import java.io.IOException;
import java.net.Socket;

public class SocketHandler {

    private RequestRouter requestRouter;
    private MyInputReader myInputReader;
    private String clientRequest;

    public SocketHandler(RequestRouter requestRouter) {
        this.requestRouter = requestRouter;
        myInputReader = new MyInputReader();
    }

    public void handleRequest(Socket socket) {
        try {
            clientRequest = myInputReader.readInput(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestRouter.route(clientRequest);
    }
}
