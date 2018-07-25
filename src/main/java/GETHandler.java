public class GETHandler {

    private FileReader fileReader;

    public GETHandler() {
        fileReader = new FileReader();
    }

//    String fullResponse = "HTTP/1.1 200 OK\n\n" + response;
    public String handleGET(String URI) {
        return fileReader.convertFile("/Users/devlin/cob_spec/public" + URI);
    }
}
