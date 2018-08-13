package http.Request;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class RequestParser {

    public Request parse(InputStream clientIn) throws IOException {
        RequestReader requestReader = new RequestReader(clientIn);
        String[] requestLine = requestReader.extractRequestLine().split(" ");
        HTTPVerb httpVerb = matchHTTPVerb(requestLine[0]);
        String resourcePath = requestLine[1];
        HashMap<String, String> headers = setRequestHeaders(requestReader.extractHeaders());
        String bodyContent = setBodyContent(headers, requestReader);
        return new Request(httpVerb, resourcePath, headers, bodyContent);
    }

    private HTTPVerb matchHTTPVerb(String requestedVerb) {
        return HTTPVerb.find(requestedVerb);
    }

    private HashMap<String, String> setRequestHeaders(String potentialHeaders) {
        return (!potentialHeaders.isEmpty()) ? assembleHeaders(potentialHeaders) : new HashMap<>();
    }

    private HashMap<String, String> assembleHeaders(String allHeaders) {
        String[] headersCollection = allHeaders.split("[\\r\\n]+");
        return mapHeaders(headersCollection);
    }

    private HashMap<String, String> mapHeaders(String[] headersCollection) {
        HashMap<String, String> newHeaders = new HashMap<>();
        for (String header : headersCollection) {
            String[] headerElements = header.split(":", 2);
            newHeaders.put(headerElements[0].trim(), headerElements[1].trim());
        }
        return newHeaders;
    }

    private String setBodyContent(HashMap<String, String> headers, RequestReader requestReader) throws IOException {
        return contentLengthExists(headers) ? requestReader.extractBodyContent
                    (Integer.parseInt(headers.get("Content-Length").trim())) : "";
    }

    private boolean contentLengthExists(HashMap<String, String> headers) {
        return headers.get("Content-Length") != null;
    }
}
