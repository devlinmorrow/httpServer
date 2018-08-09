package http.ClientConnectors;

import http.HardcodedValues;
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
    private FileOutputStream fileOutputStream;
    private BufferedWriter bufferedWriter;
    private RequestRouter requestRouter;

    public ConnectionManager(BufferedWriter bufferedWriter, RequestRouter requestRouter) {
        this.requestRouter = requestRouter;
        requestParser = new RequestParser();
        responseWriter = new ResponseWriter();
        this.bufferedWriter = bufferedWriter;
        try {
            fileOutputStream = new FileOutputStream(new File(HardcodedValues.RESOURCEPATH.getS() + "/logs"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void respondTo(Socket clientConnection) {
        connectInAndOut(clientConnection);
        Request request = requestParser.parse(clientInput);
        writeToLog(request);
        Response response = requestRouter.handle(request);
        responseWriter.write(response, clientOutput);
    }

    private void writeToLog(Request request) {
        try {
            fileOutputStream.write((request.getRequestLine() + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
