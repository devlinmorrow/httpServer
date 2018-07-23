import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketStub extends Socket {

    private BufferedReader reader;
    private PrintStream printer;

    public SocketStub(BufferedReader reader, PrintStream printer) {
        this.reader = reader;
        this.printer = printer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintStream getPrinter() {
        return printer;
    }
}
