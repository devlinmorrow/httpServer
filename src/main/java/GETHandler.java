public class GETHandler {

    private FileReader fileReader;

    public GETHandler() {
        fileReader = new FileReader();
    }

    public String handleGET(String URI) {
        return fileReader.convertFile("/Users/devlin/cob_spec/public" + URI);
    }
}
