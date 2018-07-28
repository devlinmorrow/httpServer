package http;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ConnectionAcceptorTest {

    @Test
    public void printsAcceptedConnection() throws IOException {
        IOHelper stdIO = new IOHelper("");

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(stdIO.getPrintStream(),
                new ServerSocketSpy(new SocketStubSpy("")), new ClientConnectionManagerSpy(), new ServerStatusStub(1));

        connectionAcceptor.start();

        TestCase.assertEquals(Message.REQUESTMADE.getS() + "\n", stdIO.getStringOutput());
    }

    @Test
    public void respondToClientRequestIsCalled() throws IOException {
        IOHelper stdIO = new IOHelper("");

        ClientConnectionManagerSpy socketHandlerSpy = new ClientConnectionManagerSpy();

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(stdIO.getPrintStream(),
                new ServerSocketSpy(new SocketStubSpy("")),
                socketHandlerSpy, new ServerStatusStub(1));

        connectionAcceptor.start();

        assertTrue(socketHandlerSpy.wasHandleRequestCalled());
    }

    @Test
    public void serverSocketAcceptIsCalled() throws IOException {
        IOHelper stdIO = new IOHelper("");

        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(new SocketStubSpy(""));

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(stdIO.getPrintStream(),
                serverSocketSpy, new ClientConnectionManagerSpy(), new ServerStatusStub(1));

        connectionAcceptor.start();

        assertEquals(1, serverSocketSpy.howManyTimesAcceptCalled());
    }

    @Test
    public void acceptIsCalledTwiceForTwoRequests() throws IOException {
        IOHelper stdIO = new IOHelper("");

        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(new SocketStubSpy(""));

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(stdIO.getPrintStream(),
                serverSocketSpy, new ClientConnectionManagerSpy(), new ServerStatusStub(2));

        connectionAcceptor.start();

        assertEquals(2, serverSocketSpy.howManyTimesAcceptCalled());
    }

    @Test
    public void clientSocketClosed() throws IOException {
        IOHelper stdIO = new IOHelper("");

        SocketStubSpy socketStubSpy = new SocketStubSpy("");

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(stdIO.getPrintStream(),
                new ServerSocketSpy(socketStubSpy), new ClientConnectionManagerSpy(), new ServerStatusStub(1));

        connectionAcceptor.start();

        assertTrue(socketStubSpy.wasClosedCalled());
    }
}
