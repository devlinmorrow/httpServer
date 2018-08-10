package http.ClientConnectors;

import http.Logger;
import http.Responders.ResponseStatus;
import http.Responders.RequestRouter;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectionManagerTest {

    @Test
    public void respondTo_GETRequest_WithNotFoundResponse() {
        Logger logger = new Logger();
        String GETRequest = "GET /non-existent-file123.txt HTTP/1.1";
        SocketStubSpy request = new SocketStubSpy(GETRequest);
        RequestRouter requestRouter = new RequestRouter("/", logger);

        ConnectionManager connectionManager = new ConnectionManager(requestRouter, logger);

        connectionManager.respondTo(request);

        String expectedResponse = "HTTP/1.1 " + new String(ResponseStatus.NOTFOUND.getPhrase())
                + "\r\n\r\n" + new String(ResponseStatus.NOTFOUND.getStatusBody());

        assertEquals(expectedResponse, request.getOutputS());
    }

    @Test
    public void writeLog() {
        Logger logger = new Logger();
        RequestRouter requestRouter = new RequestRouter("/", logger);

        String getRequestOne = "GET /non-existent-file123 HTTP/1.1";
        SocketStubSpy mockRequest = new SocketStubSpy(getRequestOne);
        String getRequestTwo = "GET /non-existent-file987 HTTP/1.1";
        SocketStubSpy mockRequest2 = new SocketStubSpy(getRequestTwo);

        ConnectionManager connectionManager = new ConnectionManager(requestRouter, logger);

        connectionManager.respondTo(mockRequest);
        connectionManager.respondTo(mockRequest2);

        assertEquals(getRequestOne + "\n" + getRequestTwo + "\n", logger.getLogsBody());
    }
}