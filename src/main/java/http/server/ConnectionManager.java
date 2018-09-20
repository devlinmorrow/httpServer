package http.server;

import http.Handlers.RequestRouter;
import http.Request.Request;
import http.Request.RequestParser;
import http.util.Logger;

import java.io.IOException;
import java.net.Socket;

public class ConnectionManager {

    private RequestParser requestParser;
    private ResponseConstructor responseConstructor;
    private RequestRouter requestRouter;
    private Logger logger;
    private ResponseWriter responseWriter;

    public ConnectionManager(RequestRouter requestRouter, Logger logger) {
        this.requestRouter = requestRouter;
        this.logger = logger;
        requestParser = new RequestParser();
        responseConstructor = new ResponseConstructor();
        responseWriter = new ResponseWriter();
    }

    public void respondTo(Socket clientConnection) throws IOException {
        responseWriter.writeToSocket(getResponse(clientConnection), clientConnection.getOutputStream());
        clientConnection.close();
    }

    private byte[] getResponse(Socket clientConnection) throws IOException {
        Request request = getRequest(clientConnection);
        writeToLog(request);
        return responseConstructor.construct(requestRouter.handle(request));
    }

    private Request getRequest(Socket clientConnection) throws IOException {
        return requestParser.parse(clientConnection.getInputStream());
    }

    private void writeToLog(Request request) {
        logger.addLog(request.getRequestLine());
    }
}
