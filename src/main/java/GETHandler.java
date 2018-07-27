public class GETHandler extends Handler {

    private FileContentConverter fileContentConverter;

    public GETHandler() {
        fileContentConverter = new FileContentConverter();
    }

    @Override
    public void executeRequest(Request request, Response response) {
        response.setBodyContent(fileContentConverter.getFileContents(request.getURI()));
        response.setStatus(ResponseStatus.TWOHUNDRED);
    }


}
