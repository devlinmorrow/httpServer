import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionAcceptor {

    private ServerSocket serverSocket;
    private PrintStream stdOut;
    private Router router;

    public ConnectionAcceptor(ServerSocket serverSocket, PrintStream stdOut, Router router) {
        this.serverSocket = serverSocket;
        this.stdOut = stdOut;
        this.router = router;
    }

    public void start() {
            try {
                routeRequest(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stdOut.print(Message.CONNECTED.getS());
    }

    private void routeRequest(Socket clientSocket) {
            router.route(clientSocket);
    }
}
