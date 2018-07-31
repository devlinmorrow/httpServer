package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class RequestResponder {

    private HandlerFactory handlerFactory;
    private Response response;
    private Request request;

    public RequestResponder() {
        handlerFactory = new HandlerFactory();
    }

    public Response respondTo(Request request) {
        this.request = request;
        response = new Response();
        File resource = new File(request.getURI());
        HTTPVerb httpVerb = request.getHTTPVerb();
        System.out.println(httpVerb.getLabel());
        if (methodNotAllowed(httpVerb, resource.getName())) {
            setMethodNotAllowedResponse();
        } else {
            Handler handler = handlerFactory.buildHandler(httpVerb);
            response = handler.handleRequest(request);
        }
        return response;
    }
//        } else {
//            byte[] fullContent = fileContentConverter.getContents(resource);
//            if (request.getHeaders().get("Range") != null) {
//                performRangeRequest(fullContent);
//            } else {
//                performfullGETRequest(fullContent);
//            }
//        }

    private boolean methodNotAllowed(HTTPVerb httpVerb, String resourceName) {
        if (resourceName.toLowerCase().contains("logs")) {
            return httpVerb.isNotAllowedForLogs();
        } else {
            return httpVerb.isNotAllowed();
        }
    }

    private void setMethodNotAllowedResponse() {
        response.setStatus(ResponseStatus.METHODNOTALLOWED);
        response.setBodyContent(ResponseStatus.METHODNOTALLOWED.getStatusBody());
    }

    private void performRangeRequest(byte[] fullContent) {
        String rangeSpecification = request.getHeaders().get("Range");
        String str = rangeSpecification.replaceAll("[^0-9]+", " ");
        List<String> range = Arrays.asList(str.trim().split(" "));
        int[] numberRange = range.stream().mapToInt(Integer::parseInt).toArray();
        byte[] specifiedContent = Arrays.copyOfRange(fullContent, numberRange[0], numberRange[1]);
        response.setBodyContent(specifiedContent);
        response.setStatus(ResponseStatus.PARTIALCONTENT);
    }


}
