package http.ClientConnectors;

import http.Logger;
import http.Requesters.Request;
import http.Requesters.RequestParser;
import http.Responders.RequestRouter;
import http.Responders.Response;
import http.Responders.ResponseWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectionManager {

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
        InputStream clientInput = setClientIn(clientConnection);
        OutputStream clientOutput = setClientOut(clientConnection);
        Request request = requestParser.parse(clientInput);
        writeToLog(request);
        Response response = requestRouter.handle(request);
        responseWriter.write(response, clientOutput);
        try {
            clientConnection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private InputStream setClientIn(Socket clientConnection) {
        InputStream clientInput = null;
        try {
            clientInput = clientConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientInput;
    }

    private OutputStream setClientOut(Socket clientConnection) {
        OutputStream clientOutput = null;
        try {
            clientOutput = clientConnection.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clientOutput;
    }

    private void writeToLog(Request request) {
        logger.addLog(request.getRequestLine());
    }
}
