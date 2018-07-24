import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        Router router = new Router();
        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(serverSocket, System.out, router);
        connectionAcceptor.start();
    }
}
