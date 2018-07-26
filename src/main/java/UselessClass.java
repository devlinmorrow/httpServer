//import java.io.File;
//
//public class UselessClass {
//
//    private String fullURI;
//    private FileReader fileReader;
//    private StringBuilder fullResponse;
//    private String httpVersion;
//    private String space;
//    private String responseStatus;
//    private String blankLine;
//    private Request request;
//
//    public RequestServicer() {
//        fileReader = new FileReader();
//        httpVersion = "HTTP/1.1";
//        space = " ";
//        responseStatus = "200 OK";
//        blankLine = "\n\n";
//    }
//
//    public String serviceRequest(Request request) {
//        setRequest(request);
//        setURI();
//        responseStatus = findResponseStatus().getReasonPhrase();
//        makeResponse()
//    }
//
//    private ResponseStatus findResponseStatus() {
//        return (checkIfResourceExists()) ? ResponseStatus.TWOHUNDRED : ResponseStatus.FOUROHFOUR;
//    }
//
//    private boolean checkIfResourceExists() {
//        File file = new File(fullURI);
//        return file.exists();
//    }
//
//    private Response makeResponse() {
//        fullResponse = new StringBuilder();
//        setStatusLine();
//        makeStatusLine();
//        return fullResponse;
//    }
//
//    private void setRequest(Request request) {
//        this.request = request;
//    }
//
//    private void setURI() {
//        fullURI = "/Users/devlin/cob_spec/public" + request.getURI();
//    }
//
//    private void setStatusLine() {
//        appendHTTPVersion();
//    }
//
//    private String makeStatusLine() {
//        fullResponse.append(responseStatus);
//        fullResponse.append(blankLine);
//        fullResponse.append(getFileContents());
//        return String.valueOf(fullResponse);
//
//    }
//
//    private String getFileContents() {
//        return fileReader.convertFile(fullURI);
//    }
//
//    private void appendHTTPVersion() {
//        fullResponse.append(httpVersion).append(space);
//    }
//}
//}
