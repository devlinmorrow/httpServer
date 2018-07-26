import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionAcceptor {

    private PrintStream stdOut;
    private ServerSocket serverSocket;
    private ClientConnectionManager clientConnectionManager;
    private ServerStatus serverStatus;

    public ConnectionAcceptor(PrintStream stdOut, ServerSocket serverSocket, ClientConnectionManager clientConnectionManager, ServerStatus serverStatus) {
        this.stdOut = stdOut;
        this.serverSocket = serverSocket;
        this.clientConnectionManager = clientConnectionManager;
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
        clientConnectionManager.handleRequest(socket);
    }
}
