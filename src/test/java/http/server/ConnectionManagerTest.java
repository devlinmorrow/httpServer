package http.server;

import http.util.Logger;
import http.Handlers.RequestRouter;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ConnectionManagerTest {

    private final String requestInput = "GET /testFile1.txt HTTP/1.1";
    private final String rootPath = "src/test/resources";
    private final Logger logger = new Logger();
    private final RequestRouter requestRouter = new RequestRouter(rootPath, logger);
    private final ConnectionManager connectionManager = new ConnectionManager(requestRouter, logger);
    private final SocketStubSpy clientConnection = new SocketStubSpy(requestInput);

    @Test
    public void givenRequestToRespondTo_responseWrittenAndLogWrittenAndConnectionClosed() throws IOException {
        connectionManager.respondTo(clientConnection);

        assertFalse(clientConnection.getOutputS().isEmpty());
        assertFalse(logger.getLogsBody().isEmpty());
        assertTrue(clientConnection.wasClosedCalled());
    }
}