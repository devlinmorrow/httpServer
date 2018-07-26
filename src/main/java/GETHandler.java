import java.io.File;

public class GETHandler extends Handler {

    private String fullURI;
    private Request request;
    private Response response;
    private File file;
    private FileReader fileReader;

    @Override
    public ResponseStatus executeRequest(Request request, Response response) {
        this.request = request;
        this.response = response;
        fileReader = new FileReader();
        setFile();
        if (!checkIfResourceExists()) {
            response.setBodyContent(ResponseStatus.FOUROHFOUR.getStatusBody());
            return ResponseStatus.FOUROHFOUR;
        } else {
            response.setBodyContent(fileReader.convertFile(request.getURI()));
            return ResponseStatus.TWOHUNDRED;
        }
    }

    private boolean checkIfResourceExists() {
        return file.exists();
    }

    private void setFile() {
        file = new File(request.getURI());
    }

}
