package http.Responders;

import http.Requesters.Request;

public interface Handler {

    Response handle(Request request);

}
