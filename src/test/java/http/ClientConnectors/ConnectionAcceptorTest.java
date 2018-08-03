package http.ClientConnectors;

import http.HardcodedValues;
import http.IOHelper;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ConnectionAcceptorTest {
//
//    @Test
//    public void respondToClientConnectionIsCalled() throws IOException {
//        ClientConnectionManagerSpy connectionManagerSpy = new ClientConnectionManagerSpy();
//
//        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(makeStdOut(),
//                new ServerSocketSpy(new SocketStubSpy("")),
//                connectionManagerSpy, new ServerStatusStub(1));
//
//        connectionAcceptor.start();
//
//        assertTrue(connectionManagerSpy.wasRespondToRequestCalled());
//    }
//
//    @Test
//    public void acceptIsCalledTwiceForTwoRequests() throws IOException {
//        ServerSocketSpy serverSocketSpy = new ServerSocketSpy(new SocketStubSpy(""));
//
//        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(makeStdOut(),
//                serverSocketSpy, new ClientConnectionManagerSpy(), new ServerStatusStub(2));
//
//        connectionAcceptor.start();
//
//        assertEquals(2, serverSocketSpy.howManyTimesAcceptCalled());
//    }
//
//    @Test
//    public void clientSocketClosed() throws IOException {
//        SocketStubSpy socketStubSpy = new SocketStubSpy("");
//
//        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(makeStdOut(),
//                new ServerSocketSpy(socketStubSpy), new ClientConnectionManagerSpy(),
//                new ServerStatusStub(1));
//
//        connectionAcceptor.start();
//
//        assertTrue(socketStubSpy.wasClosedCalled());
//    }
//
//    @Test
//    public void startLog() throws IOException {
//        Path logsPath = Paths.get(HardcodedValues.RESOURCEPATH.getS() + "/logs");
//        if (Files.exists(logsPath)) {
//            Files.delete(logsPath);
//        }
//
//        SocketStubSpy socketStubSpy = new SocketStubSpy("");
//
//        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor(makeStdOut(),
//                new ServerSocketSpy(socketStubSpy), new ClientConnectionManagerSpy(),
//                new ServerStatusStub(0));
//
//        connectionAcceptor.start();
//
//        assertTrue(Files.exists(Paths.get(HardcodedValues.RESOURCEPATH.getS() + "/logs")));
//    }
//
//    private PrintStream makeStdOut() {
//        IOHelper stdIO = new IOHelper("");
//        return stdIO.getPrintStream();
//    }
}
