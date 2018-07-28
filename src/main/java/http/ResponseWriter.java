package http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ResponseWriter {

    public void write(Response response, OutputStream clientResponseOutput) {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        try {
            responseStream.write(response.getHttpVersion());
            responseStream.write(" ".getBytes());
            responseStream.write(response.getStatus().getPhrase());
            responseStream.write("\n".getBytes());


            for (Map.Entry<Header, byte[]> entry : response.getHeaders().entrySet()) {
                responseStream.write(entry.getKey().getLabel());
                responseStream.write(entry.getValue());
                responseStream.write("\n".getBytes());
            }

            responseStream.write("\n".getBytes());
            responseStream.write(response.getBodyContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeToSocket(responseStream.toByteArray(), clientResponseOutput);
    }

    private void writeToSocket(byte[] response, OutputStream clientResponseOutput) {
        try {
            clientResponseOutput.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
