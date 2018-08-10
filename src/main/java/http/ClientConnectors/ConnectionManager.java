package http.ClientConnectors;

import http.HardcodedValues;
import http.Logger;
import http.Requesters.Request;
import http.Requesters.RequestParser;
import http.Responders.Response;
import http.Responders.ResponseWriter;
import http.Responders.RequestRouter;

import java.io.*;
import java.net.Socket;

public class ConnectionManager {

    private InputStream clientInput;
    private OutputStream clientOutput;
    private RequestParser requestParser;
    private ResponseWriter responseWriter;
    private RequestRouter requestRouter;
    private Logger logger;

    public ConnectionManager(RequestRouter requestRouter, Logger logger) {
        this.requestRouter = requestRouter;
        this.logger = logger;
        requestParser = new RequestParser();
        responseWriter = new ResponseWriter();
    }

    public void respondTo(Socket clientConnection) {
        connectInAndOut(clientConnection);
        Request request = requestParser.parse(clientInput);
        writeToLog(request);
        Response response = requestRouter.handle(request);
        responseWriter.write(response, clientOutput);
    }

    private void writeToLog(Request request) {
        logger.addLog(request.getRequestLine());
    }

    private void connectInAndOut(Socket clientConnection) {
        try {
            clientInput = clientConnection.getInputStream();
            clientOutput = clientConnection.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
