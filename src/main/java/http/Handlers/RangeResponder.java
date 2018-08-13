package http.Handlers;

import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;
import http.util.FileContentConverter;
import http.util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RangeResponder {

    private String rootPath;
    private FileContentConverter fileContentConverter;
    private ResourceTypeIdentifier resourceTypeIdentifier;

    public RangeResponder(String rootPath, FileContentConverter fileContentConverter, ResourceTypeIdentifier resourceTypeIdentifier) {
        this.rootPath = rootPath;
        this.fileContentConverter = fileContentConverter;
        this.resourceTypeIdentifier = resourceTypeIdentifier;
    }

    public Response performRange(Request request) throws IOException {
        File resource = new File(rootPath + request.getResourcePath());
        byte[] fullContents = fileContentConverter.getFullContents(resource);
        String rangeSlice = getRangePart(request);
        ArrayList<Integer> rangeLimits = getRangeLimits(fullContents, rangeSlice);
        Response response = getResponse(fullContents, rangeLimits.get(0), rangeLimits.get(1));
        response.setContentTypeHeader(resourceTypeIdentifier.getType(resource));
        return response;
    }

    private String getRangePart(Request request) {
        String rangeSpec = request.getHeaders().get("Range");
        return rangeSpec.replaceAll("[^0-9-0-9]+", " ").trim();
    }

    private ArrayList<Integer> getRangeLimits(byte[] fullContents, String rangeSlice) {
        int first;
        int last;
        String[] rangeNumbers = rangeSlice.split("-");
        if (bothLimitsExist(rangeSlice)) {
            first = Integer.parseInt(rangeNumbers[0]);
            last = Integer.parseInt(rangeNumbers[1]);
        } else if (firstLimitOnly(rangeSlice)) {
            first = Integer.parseInt(rangeNumbers[0]);
            last = fullContents.length - 1;
        } else {
            int number = Integer.parseInt(rangeNumbers[1]);
            first = fullContents.length - number;
            last = fullContents.length - 1;
        }
        return new ArrayList<>(Arrays.asList(first, last));
    }

    private boolean bothLimitsExist(String rangeSlice) {
        return rangeSlice.matches("[0-9]+-[0-9]+");
    }

    private boolean firstLimitOnly(String rangeSlice) {
        return rangeSlice.matches("[0-9]+-");
    }

    private Response getResponse(byte[] fullContents, int first, int last) {
        return outOfRange(fullContents, first, last) ? setRangeNotSatisfiableResponse(fullContents) :
                setRangeResponse(fullContents, first, last);
    }

    private boolean outOfRange(byte[] fullContents, int first, int last) {
        return outOfBounds(fullContents, first) || outOfBounds(fullContents, last);
    }

    private boolean outOfBounds(byte[] contents, int number) {
        return number > contents.length || number < 0;
    }

    private Response setRangeNotSatisfiableResponse(byte[] fullContents) {
        Response response = new Response();
        response.setContentRangeHeader(("bytes " + "*/" + Integer.toString(fullContents.length)));
        response.setBodyContent(ResponseStatus.RANGENOTSATISFIABLE.getStatusBody());
        response.setStatus(ResponseStatus.RANGENOTSATISFIABLE);
        return response;
    }

    private Response setRangeResponse(byte[] fullContents, int first, int last) {
        Response response = new Response();
        byte[] specifiedContents = fileContentConverter.getPartialContent(fullContents, first, last);
        response.setBodyContent(specifiedContents);
        response.setContentRangeHeader(("bytes " + Integer.toString(first)
                + "-" + Integer.toString(last) + "/" + Integer.toString(fullContents.length)));
        response.setStatus(ResponseStatus.PARTIALCONTENT);
        return response;
    }
}
