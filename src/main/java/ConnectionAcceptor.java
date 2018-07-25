import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionAcceptor {

    private PrintStream stdOut;
    private ServerSocket serverSocket;
    private SocketHandler socketHandler;
    private ServerStatus serverStatus;

    public ConnectionAcceptor(PrintStream stdOut, ServerSocket serverSocket, SocketHandler socketHandler, ServerStatus serverStatus) {
        this.stdOut = stdOut;
        this.serverSocket = serverSocket;
        this.socketHandler = socketHandler;
        this.serverStatus = serverStatus;
    }

    public void start() {
            try {
                while (serverStatus.isRunning()) {
                    handleRequest(serverSocket.accept());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void handleRequest(Socket clientSocket) throws IOException {
        stdOut.println(Message.REQUESTMADE.getS());
        respondToRequest(clientSocket);
        clientSocket.close();
    }

    private void respondToRequest(Socket socket) {
        socketHandler.handleRequest(socket);
    }
}
