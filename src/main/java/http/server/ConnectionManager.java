package http.server;

import http.Handlers.RequestRouter;
import http.Request.Request;
import http.Request.RequestParser;
import http.Response.Response;
import http.util.Logger;

import java.io.IOException;
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

    public void respondTo(Socket clientConnection) throws IOException {
        responseWriter.write(getReponse(clientConnection), clientConnection.getOutputStream());
        clientConnection.close();
    }

    private Response getReponse(Socket clientConnection) throws IOException {
        Request request = getRequest(clientConnection);
        writeToLog(request);
        return requestRouter.handle(request);
    }

    private Request getRequest(Socket clientConnection) throws IOException {
        return requestParser.parse(clientConnection.getInputStream());
    }

    private void writeToLog(Request request) {
        logger.addLog(request.getRequestLine());
    }
}
