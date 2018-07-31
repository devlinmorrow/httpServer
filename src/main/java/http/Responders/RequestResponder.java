package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class RequestResponder {

    private FileContentConverter fileContentConverter;
    private Response response;
    private Request request;

    public RequestResponder() {
        fileContentConverter = new FileContentConverter();
    }

    public Response respondTo(Request request) {
        this.request = request;
        response = new Response();
        File resource = new File(request.getURI());
        HTTPVerb httpVerb = request.getHTTPVerb();
        if (methodNotAllowed(httpVerb, resource.getName())) {
            setMethodNotAllowedResponse();
        } else if (httpVerb == HTTPVerb.OPTIONS) {
            OPTIONSHandler optionsHandler = new OPTIONSHandler();
            response = optionsHandler.handleRequest(request);
        } else {
            if (httpVerb == HTTPVerb.GET || httpVerb == HTTPVerb.HEAD) {
                    GETFileHandler getFileHandler = new GETFileHandler();
                    response = getFileHandler.handleRequest(request);
            }
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

    private void performOPTIONSRequest(String resourceName) {
        String allowedMethods;
        if (resourceName.toLowerCase().contains("logs")) {
            allowedMethods = HTTPVerb.getAllowedForLogsMethods();
        } else {
            allowedMethods = HTTPVerb.getAllowedMethods();
        }
        response.setAllowHeader(allowedMethods);
        response.setStatus(ResponseStatus.OK);
    }

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

    private void performHEADRequest() {
        response.clearAllExceptStatusLine();
        response.setStatus(ResponseStatus.OK);
    }

    private boolean doesNotExist(File requestedFile) {
        return !requestedFile.exists();
    }

    private void setResourceNotFoundResponse() {
        response.setStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBody());
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
