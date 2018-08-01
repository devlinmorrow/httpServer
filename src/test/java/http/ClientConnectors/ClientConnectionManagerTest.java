package http.ClientConnectors;

import http.Responders.ResponseStatus;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientConnectionManagerTest {

    @Test
    public void respondTo_GETRequest_WithNotFoundResponse() {
        String GETRequest = "GET /non-existent-file123 HTTP/1.1";
        SocketStubSpy mockRequest = new SocketStubSpy(GETRequest);

        ClientConnectionManager clientConnectionManager = new ClientConnectionManager();
        clientConnectionManager.respondTo(mockRequest);

        String expectedResponse = "HTTP/1.1 " + new String(ResponseStatus.NOTFOUND.getPhrase())
                + "\r\n\r\n" + new String(ResponseStatus.NOTFOUND.getStatusBody());

        assertEquals(expectedResponse, mockRequest.getOutputS());
    }
}