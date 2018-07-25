public class GETHandler {

    private FileConverter fileConverter;

    public GETHandler() {
        fileConverter = new FileConverter();
    }

    public String handleGET(String clientRequest) {
        return fileConverter.convertFile("/Users/devlin/cob_spec/public"
                + findFilePath(clientRequest));
    }

    private String findFilePath(String request) {
//        return stringAnalyser.findFilePathName(request);
        return "";
    }
}
