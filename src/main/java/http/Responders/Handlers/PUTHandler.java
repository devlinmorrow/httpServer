package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PUTHandler extends Handler {

    private String rootPath;
    private Request request;
    private Response response;

    public PUTHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HTTPVerb.PUT);
        addHandledPathSegment("txt");
    }

    @Override
    public Response getResponse(Request request) {
        this.request = request;
        response = new Response();
        if (Files.exists(Paths.get(rootPath + request.getResourcePath()))) {
            performUpdateResponse();
        } else {
            performCreateResponse();
        }
        return response;
    }

    private void performCreateResponse() {
        writeFile();
        response.setStatus(ResponseStatus.CREATED);
    }

    private void performUpdateResponse() {
        writeFile();
        response.setStatus(ResponseStatus.OK);
    }


    private void writeFile() {
        try {
            Files.write(Paths.get(rootPath + request.getResourcePath()),
                    request.getBodyContent().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
