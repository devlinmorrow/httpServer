package http.ClientConnectors;

import http.HardcodedValues;
import http.Requesters.Request;
import http.Requesters.RequestParser;
import http.Responders.RequestResponder;
import http.Responders.Response;
import http.Responders.ResponseWriter;

import java.io.*;
import java.net.Socket;

public class ClientConnectionManager {

    private InputStream clientInput;
    private OutputStream clientOutput;
    private RequestParser requestParser;
    private RequestResponder requestResponder;
    private ResponseWriter responseWriter;
    private FileOutputStream fileOutputStream;
    private BufferedWriter bufferedWriter;

    public ClientConnectionManager(BufferedWriter bufferedWriter) {
        requestParser = new RequestParser();
        requestResponder = new RequestResponder();
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
        Response response = requestResponder.respondTo(request);
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
