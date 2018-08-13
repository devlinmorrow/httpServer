package http.server;

import http.Response.Response;
import http.Response.ResponseHeader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ResponseWriter {

    public void write(Response response, OutputStream clientResponseOutput) throws IOException {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        responseStream.write(response.getHttpVersion());
        responseStream.write(" ".getBytes());
        responseStream.write(response.getStatus().getPhrase());
        responseStream.write("\r\n".getBytes());

        for (Map.Entry<ResponseHeader, byte[]> entry : response.getHeaders().entrySet()) {
            responseStream.write(entry.getKey().getLabel());
            responseStream.write(": ".getBytes());
            responseStream.write(entry.getValue());
            responseStream.write("\r\n".getBytes());
        }

        responseStream.write("\r\n".getBytes());
        responseStream.write(response.getBodyContent());
        writeToSocket(responseStream.toByteArray(), clientResponseOutput);
    }

    private void writeToSocket(byte[] response, OutputStream clientResponseOutput) throws IOException {
        clientResponseOutput.write(response);
    }
}
