package http.Requesters;

import http.HardcodedValues;

import java.io.InputStream;
import java.util.HashMap;

public class RequestParser {

    private HTTPVerb httpVerb;
    private String URI;
    private HashMap<String, String> headers;
    private String bodyContent;

    public Request parse(InputStream clientIn) {
        RequestReader requestReader = new RequestReader(clientIn);
        setRequestElements(requestReader);
        return new Request(httpVerb, URI, headers, bodyContent);
    }

    private void setRequestElements(RequestReader requestReader) {
        String[] requestLine = requestReader.extractRequestLine().split(" ");
        httpVerb = matchHTTPVerb(requestLine[0]);
        URI = requestLine[1];
        String potentialHeaders = requestReader.extractHeaders();
        if (!potentialHeaders.isEmpty()) {
            headers = assembleHeaders(potentialHeaders);
        } else {
            headers = new HashMap<>();
        }
        if (headers.get("Content-Length") != null) {
            bodyContent = requestReader.extractBodyContent
                    (Integer.parseInt(headers.get("Content-Length").trim()));
        } else {
            bodyContent = "";
        }
    }

    private HTTPVerb matchHTTPVerb(String requestedVerb) {
        return HTTPVerb.find(requestedVerb);
    }

    private String getFullURI(String requestedURI) {
        return HardcodedValues.RESOURCEPATH.getS() + requestedURI;
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

}
