public class GETHandler extends Handler {

    private Request request;
    private Response response;
    private TextFileConverter textFileConverter;

    public GETHandler() {
        textFileConverter = new TextFileConverter();
    }

    @Override
    public void executeRequest(Request request, Response response) {
        this.request = request;
        this.response = response;

        response.setBodyContent(textFileConverter.getFileContents(request.getURI()));
        response.setStatus(ResponseStatus.TWOHUNDRED);
    }


}
