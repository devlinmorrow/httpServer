import java.io.File;

public class GETHandler extends Handler {

    private Request request;
    private Response response;
    private File file;
    private FileReader fileReader;

    public GETHandler() {
        fileReader = new FileReader();
    }

    @Override
    public void executeRequest(Request request, Response response) {
        this.request = request;
        this.response = response;
        response.setBodyContent(fileReader.convertFile(request.getURI()));
        response.setStatus(ResponseStatus.TWOHUNDRED);
    }


}
