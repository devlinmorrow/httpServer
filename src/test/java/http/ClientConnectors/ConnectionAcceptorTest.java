package http.ClientConnectors;

import http.IOHelper;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ConnectionAcceptorTest {

    @Test
    public void respondToClientConnectionIsCalled() throws IOException {
        ClientConnectionManagerSpy connectionManagerSpy = new ClientConnectionManagerSpy();

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(makeStdOut(),
                new ServerSocketSpy(new SocketStubSpy("")),
                connectionManagerSpy, new ServerStatusStub(1));

        connectionAcceptor.start();

        assertTrue(connectionManagerSpy.wasRespondToRequestCalled());
    }

    @Test
    public void acceptIsCalledTwiceForTwoRequests() throws IOException {
        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(new SocketStubSpy(""));

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(makeStdOut(),
                serverSocketSpy, new ClientConnectionManagerSpy(), new ServerStatusStub(2));

        connectionAcceptor.start();

        assertEquals(2, serverSocketSpy.howManyTimesAcceptCalled());
    }

    @Test
    public void clientSocketClosed() throws IOException {
        SocketStubSpy socketStubSpy = new SocketStubSpy("");

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(makeStdOut(),
                new ServerSocketSpy(socketStubSpy), new ClientConnectionManagerSpy(),
                new ServerStatusStub(1));

        connectionAcceptor.start();

        assertTrue(socketStubSpy.wasClosedCalled());
    }

    private PrintStream makeStdOut() {
        IOHelper stdIO = new IOHelper("");
        return stdIO.getPrintStream();
    }
}
