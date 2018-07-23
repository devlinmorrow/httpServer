import org.junit.Test;

import java.io.*;

import static junit.framework.TestCase.assertEquals;

public class ConnectionAcceptorTest {

    private final static String exampleMessage = "exampleMessage";

    @Test
    public void acceptsConnection() throws IOException {
        OutputStream serverOut = new ByteArrayOutputStream();
        PrintStream serverPrinter = new PrintStream(serverOut);

        BufferedReader socketReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream("".getBytes())));
        OutputStream socketOut = new ByteArrayOutputStream();
        PrintStream socketPrinter = new PrintStream(socketOut);

        SocketStub socketStub = new SocketStub(socketReader, socketPrinter);
        ServerSocketStub serverSocketStub = new ServerSocketStub(socketStub);

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(serverSocketStub, serverPrinter);

        connectionAcceptor.start();

        assertEquals(Message.CONNECTED.getStringValue(), serverOut.toString());

    }
}
