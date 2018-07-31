package http.Responders;

import http.Requesters.Request;

public class ERRORHandler implements Handler {

    public Response handleRequest(Request request) {
        Response response = new Response();
        response.setStatus(ResponseStatus.SERVERERROR);
        return response;
    }
}
