import java.io.IOException;
import java.net.Socket;

public class Router {

    private GETHandler getHandler;

    public Router(GETHandler getHandler) {
        this.getHandler = getHandler;
    }

    public void route(Socket clientSocket) {
        try {
            passToHandler(clientSocket.getInputStream().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void passToHandler(String request) {
        HTTPMethod currentRequest = analyseRoute(request);
        if (currentRequest == HTTPMethod.GET) {
            getHandler.handleGET(request);
        }
    }

    private HTTPMethod analyseRoute(String request) {
        return HTTPMethod.GET;
    }

}
