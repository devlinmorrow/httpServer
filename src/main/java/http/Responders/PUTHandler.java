package http.Responders;

import http.Requesters.Request;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PUTHandler implements Handler {

    private Response response;
    private Request request;

    public Response handle(Request request) {
        this.request = request;
        response = new Response();
        String dataToWrite = request.getBodyContent();
        Path file = Paths.get(request.getURI());
        try {
            Files.write(file, dataToWrite.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(ResponseStatus.CREATED);
        return response;
    }

}
