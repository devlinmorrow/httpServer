package http.Responders;

import http.Requesters.Request;

public class UnrecognisedMethodHandler implements Handler {

    public Response handle(Request request) {
        Response response = new Response();
        response.setStatus(ResponseStatus.SERVERERROR);
        return response;
    }
}
