package http.ClientConnectors;

import http.Logger;
import http.Responders.RequestRouter;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ConnectionManagerTest {

    private final String requestInput = "GET /testFile1.txt HTTP/1.1";
    private final String rootPath = "src/test/resources";
    private final Logger logger = new Logger();
    private final RequestRouter requestRouter = new RequestRouter(rootPath, logger);
    ConnectionManager connectionManager = new ConnectionManager(requestRouter, logger);

    @Test
    public void whenGivenRequest_responseWritten() throws IOException {
        SocketStubSpy clientConnection = new SocketStubSpy(requestInput);

        connectionManager.respondTo(clientConnection);

        assertFalse(clientConnection.getOutputS().isEmpty());
    }

    @Test
    public void whenGivenRequest_logWritten() throws IOException {
        SocketStubSpy clientConnection = new SocketStubSpy(requestInput);

        connectionManager.respondTo(clientConnection);

        assertFalse(logger.getLogsBody().isEmpty());
    }

    @Test
    public void afterRequestIsHandled_connectionIsClosed() throws IOException {
        SocketStubSpy clientConnection = new SocketStubSpy(requestInput);

        connectionManager.respondTo(clientConnection);

        assertTrue(clientConnection.wasClosedCalled());
    }
}