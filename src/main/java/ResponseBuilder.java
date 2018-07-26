public class ResponseBuilder {

    private Response response;
    private StringBuilder responseMessage;

    public String makeResponse(Response response) {
        setResponseMessage(response);
        return makeResponse();
    }

    private String makeResponse() {
        responseMessage = new StringBuilder();
        makeStatusLine();
        return String.valueOf(responseMessage);
    }

    private void makeStatusLine() {
        appendHTTPVersion();
        responseMessage.append(response.getResponseStatus().getReasonPhrase());
        responseMessage.append(HardcodedValues.BLANKLINE.getS());
        responseMessage.append(response.getBodyContent());
    }

    private void appendHTTPVersion() {
        responseMessage.append(response.getHttpVersion()).append(" ");
    }

    private void setResponseMessage(Response response) {
        this.response = response;
    }
}
