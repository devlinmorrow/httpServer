package http.ClientConnectors;

import http.HardcodedValues;
import http.Responders.FileContentConverter;
import http.Responders.ResponseStatus;
import http.Responders.RequestRouter;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ConnectionManagerTest {

    @Test
    public void respondTo_GETRequest_WithNotFoundResponse() throws IOException {
        String GETRequest = "GET /non-existent-file123.txt HTTP/1.1";
        SocketStubSpy mockRequest = new SocketStubSpy(GETRequest);
        RequestRouter requestRouter = new RequestRouter("/");

        ConnectionManager connectionManager = new ConnectionManager
                (new BufferedWriter(new FileWriter(HardcodedValues.RESOURCEPATH.getS() + "/logs", true)), requestRouter);

        connectionManager.respondTo(mockRequest);

        String expectedResponse = "HTTP/1.1 " + new String(ResponseStatus.NOTFOUND.getPhrase())
                + "\r\n\r\n" + new String(ResponseStatus.NOTFOUND.getStatusBody());

        assertEquals(expectedResponse, mockRequest.getOutputS());
    }

    @Test
    public void writeLog() throws IOException {
        RequestRouter requestRouter = new RequestRouter("/");
        String logPathString = HardcodedValues.RESOURCEPATH.getS() + "/logs";
        Path logsPath = Paths.get(logPathString);
        if (Files.exists(logsPath)) {
            Files.delete(logsPath);
        }
        Files.createFile(logsPath);

        File theLogFile = new File(logPathString);

        String GETRequest = "GET /non-existent-file123 HTTP/1.1";
        SocketStubSpy mockRequest = new SocketStubSpy(GETRequest);
        String GETRequest2 = "GET /non-existent-file123 HTTP/1.1";
        SocketStubSpy mockRequest2 = new SocketStubSpy(GETRequest2);

        ConnectionManager connectionManager = new ConnectionManager
                (new BufferedWriter(new FileWriter(theLogFile, true)), requestRouter);

        connectionManager.respondTo(mockRequest);
        connectionManager.respondTo(mockRequest2);

        FileContentConverter fileContentConverter = new FileContentConverter();

        assertArrayEquals((GETRequest +"\n" + GETRequest + "\n").getBytes(),
                fileContentConverter.getFullContents(theLogFile));
    }
}