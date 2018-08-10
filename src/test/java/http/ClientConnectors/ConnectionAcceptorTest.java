package http.ClientConnectors;

import http.HardcodedValues; import http.IOHelper;
import http.Logger;
import http.Responders.RequestRouter;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ConnectionAcceptorTest {

    @Test
    public void respondToClientConnectionIsCalled() throws IOException {
        Logger logger = new Logger();
        ConnectionManagerSpy connectionManagerSpy = new ConnectionManagerSpy
                (new RequestRouter("/", logger), logger);

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(makeStdOut(),
                new ServerSocketSpy(new SocketStubSpy("")),
                connectionManagerSpy, new ServerStatusStub(1));

        connectionAcceptor.start();

        assertTrue(connectionManagerSpy.wasRespondToRequestCalled());
    }

    @Test
    public void acceptIsCalledTwiceForTwoRequests() throws IOException {
        Logger logger = new Logger();
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(new SocketStubSpy(""));

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(makeStdOut(),
                serverSocketSpy, new ConnectionManagerSpy(new RequestRouter("/", logger),
                logger), new ServerStatusStub(2));

        connectionAcceptor.start();

        assertEquals(2, serverSocketSpy.howManyTimesAcceptCalled());
    }

    @Test
    public void clientSocketClosed() throws IOException {
        Logger logger = new Logger();
        SocketStubSpy socketStubSpy = new SocketStubSpy("");

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(makeStdOut(),
                new ServerSocketSpy(socketStubSpy), new ConnectionManagerSpy(new RequestRouter
                ("/", logger), logger), new ServerStatusStub(1));

        connectionAcceptor.start();

        assertTrue(socketStubSpy.wasClosedCalled());
    }

    private PrintStream makeStdOut() {
        IOHelper stdIO = new IOHelper("");
        return stdIO.getPrintStream();
    }
}
