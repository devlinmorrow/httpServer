package http.Responders.Handlers;

import http.Requesters.Request;
import http.Responders.Response;

public interface Handler {

    Response handle(Request request);

}
