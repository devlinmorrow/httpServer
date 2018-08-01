package http.Responders.Handlers;

import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DELETEHandler implements Handler {

    private Request request;
    private Response response;

    @Override
    public Response handle(Request request) {
        this.request = request;
        response = new Response();
        if (Files.exists(Paths.get(request.getURI()))) {
            deleteResource();
        } else {
            setNotFound();
        }
        return response;
    }

    private void setNotFound() {
        response.setStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBody());
    }

    private void deleteResource() {
        try {
            Files.delete(Paths.get(request.getURI()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(ResponseStatus.OK);
    }
}
