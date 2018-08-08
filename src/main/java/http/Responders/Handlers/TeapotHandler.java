package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;

public class TeapotHandler extends Handler {

    public final String teaContent = "Here's some delicious tea!";

    public TeapotHandler() {
        addHandledVerb(HTTPVerb.GET);
        addHandledPathSegment("tea");
        addHandledPathSegment("coffee");
    }

    @Override
    public Response getResponse(Request request) {
        Response response = new Response();
        if (request.getResourcePath().contains("coffee")) {
            response.setStatus(ResponseStatus.IMATEAPOT);
            response.setBodyContent(ResponseStatus.IMATEAPOT.getStatusBody());
        } else {
            response.setStatus(ResponseStatus.OK);
            response.setBodyContent(teaContent.getBytes());
        }
        return response;
    }
}
