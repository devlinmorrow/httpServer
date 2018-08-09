package http.Responders;

import http.Requesters.Request;
import http.Responders.Handlers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RequestRouter {

    private final ArrayList<Handler> handlers = new ArrayList<>();

    public RequestRouter(String rootPath) {
        addHandlers(Arrays.asList(
                new BasicAuthHandler(rootPath),
                new CookieHandler(),
                new DeleteHandler(rootPath),
                new DirectoryHandler(rootPath),
                new FormHandler(rootPath, (new HashMap<>())),
                new GetHandler(rootPath),
                new OptionsHandler(),
                new ParametersHandler(),
                new PatchHandler(rootPath),
                new PutHandler(rootPath),
                new RedirectHandler(),
                new TeapotHandler()
        ));
    }

    public Response handle(Request request) {
        for (Handler handler : handlers) {
            if (handler.handles(request)) {
                return handler.getResponse(request);
            }
        }
        return methodNotAllowedResponse();
    }

    private void addHandlers(List<Handler> handlers) {
        for (Handler handler : handlers) {
            addHandler(handler);
        }
    }

    private void addHandler(Handler handler) {
        handlers.add(handler);
    }

    private Response methodNotAllowedResponse() {
        Response response = new Response();
        response.setStatus(ResponseStatus.METHODNOTALLOWED);
        return response;
    }
}
