package http;

import java.io.File;

public class RequestResponder {

    private FileContentConverter fileContentConverter;
    private Response response;

    public RequestResponder() {
        fileContentConverter = new FileContentConverter();
    }

    public Response respondTo(Request request) {
        response = new Response();
        File resource = new File(request.getURI());
        if (doesNotExist(resource)) {
            setResourceNotFoundResponse();
        } else {
            setResourceType(resource.getName());
            performGETRequest(resource);
            setOKResponse();
        }
        return response;
    }

    private boolean doesNotExist(File requestedFile) {
        return !requestedFile.exists();
    }

    private void setResourceNotFoundResponse() {
        response.setStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBody());
    }

    private void setResourceType(String resourceName) {
        ContentType contentType;
        if (resourceName.contains(".jpeg")) {
            contentType = ContentType.JPEG;
        } else if (resourceName.contains(".gif")) {
            contentType = ContentType.GIF;
        } else if (resourceName.contains(".png")) {
            contentType = ContentType.PNG;
        } else {
            contentType = ContentType.TXT;
        }
        setContentType(contentType);
    }

    private void setContentType(ContentType contentType) {
        response.getHeaders().put(HardcodedValues.CONTENTTYPEHEADER.getBytes(), contentType.getHeaderKey());
    }

    private void performGETRequest(File resource) {
        response.setBodyContent(fileContentConverter.getFileContents(resource));
    }

    private void setOKResponse() {
        response.setStatus(ResponseStatus.OK);
    }

}
