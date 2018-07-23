import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionAcceptor {

    private ServerSocket serverSocket;
    private PrintStream printer;

    public ConnectionAcceptor(ServerSocket serverSocket, PrintStream printer) {
        this.serverSocket = serverSocket;
        this.printer = printer;
    }

    public void start() {
        try {
            serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printer.print(Message.CONNECTED.getStringValue());
    }
}
