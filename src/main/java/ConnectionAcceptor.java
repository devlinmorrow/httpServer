import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionAcceptor {

    private ServerSocket serverSocket;
    private PrintStream stdOut;
    private SocketHandler socketHandler;

    public ConnectionAcceptor(ServerSocket serverSocket, PrintStream stdOut, SocketHandler socketHandler) {
        this.serverSocket = serverSocket;
        this.stdOut = stdOut;
        this.socketHandler = socketHandler;
    }

    public void start() {
            try {
                handleRequest(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
            stdOut.print(Message.CONNECTED.getS());
    }

    private void handleRequest(Socket socket) {
        socketHandler.handleRequest(socket);
    }
}
