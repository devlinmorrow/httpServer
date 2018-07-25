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
                makeServerSocketSpy(""), new RequestHandlerSpy(), new ServerStatusStub(1));

        connectionAcceptor.start();

        TestCase.assertEquals(Message.REQUESTMADE.getS() + "\n", stdIO.getStringOutput());
    }

    @Test
    public void respondToClientRequestIsCalled() throws IOException {
        IOHelper stdIO = new IOHelper("");

        RequestHandlerSpy socketHandlerSpy = new RequestHandlerSpy();

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(stdIO.getPrintStream(),
                makeServerSocketSpy(""), socketHandlerSpy, new ServerStatusStub(1));

        connectionAcceptor.start();

        assertTrue(socketHandlerSpy.wasHandleRequestCalled());
    }

    @Test
    public void serverSocketAcceptIsCalled() throws IOException {
        IOHelper stdIO = new IOHelper("");

        ServerSocketSpy serverSocketSpy = makeServerSocketSpy("");

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(stdIO.getPrintStream(),
                serverSocketSpy, new RequestHandlerSpy(), new ServerStatusStub(1));

        connectionAcceptor.start();

        assertEquals(1, serverSocketSpy.howManyTimesAcceptCalled());
    }

    @Test
    public void acceptIsCalledTwiceForTwoRequests() throws IOException {
        IOHelper stdIO = new IOHelper("");

        ServerSocketSpy serverSocketSpy = makeServerSocketSpy("");

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(stdIO.getPrintStream(),
                serverSocketSpy, new RequestHandlerSpy(), new ServerStatusStub(2));

        connectionAcceptor.start();

        assertEquals(2, serverSocketSpy.howManyTimesAcceptCalled());
    }

    @Test
    public void clientSocketClosed() throws IOException {
        IOHelper stdIO = new IOHelper("");

        IOHelper socketIO = new IOHelper("");
        SocketStubSpy socketStubSpy = new SocketStubSpy(socketIO.getIn(), socketIO.getOut());

        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(stdIO.getPrintStream(),
                new ServerSocketSpy(socketStubSpy), new RequestHandlerSpy(), new ServerStatusStub(1));

        connectionAcceptor.start();

        assertTrue(socketStubSpy.wasClosedCalled());
    }

    private ServerSocketSpy makeServerSocketSpy(String input) throws IOException {
        IOHelper socketIO = new IOHelper(input);
        SocketStubSpy socketStubSpy = new SocketStubSpy(socketIO.getIn(), socketIO.getOut());
        return new ServerSocketSpy(socketStubSpy);
    }

}
