package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Handlers.*;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Router2 {

    private final ArrayList<Handler> handlers = new ArrayList<>();

    public Router2(String rootPath) {
        addHandlers(Arrays.asList(
                new COOKIEHandler(),
                new DELETEHandler(rootPath),
                new FormHandler(new FormFields(new HashMap<>())),
                new GETHandler(rootPath),
                new OPTIONSHandler(),
                new PARAMETERHandler(),
                new PATCHHandler(rootPath),
                new PUTHandler(rootPath),
                new RedirectHandler(),
                new TeapotHandler()
        ));
    }

    public void addHandlers(List<Handler> handlers) {
        for (Handler handler : handlers) {
            addHandler(handler);
        }
    }

    public void addHandler(Handler handler) {
        handlers.add(handler);
    }

    public Response handle(Request request) {
        for (Handler handler : handlers) {
            if (handler.handles(request)) {
                return handler.getResponse(request);
            }
        }
        return methodNotAllowedResponse();
    }

    private Response methodNotAllowedResponse() {
        Response response = new Response();
        response.setStatus(ResponseStatus.METHODNOTALLOWED);
        return response;
    }
}
