public class RequestAnalyser {

    public HTTPMethod findHTTPMethod(String request) {
        String httpMethodString = findHTTPMethodString(request);
        if (httpMethodString.equals("GET")) {
            return HTTPMethod.GET;
        } else {
            return null;
        }
    }

    private String findHTTPMethodString(String request) {
        String[] linesOfRequest = splitIntoHTTPLines(request);
        String[] wordsOfLine = splitIntoWords(linesOfRequest[0]);
        return wordsOfLine[0];
    }

    private String[] splitIntoWords(String line) {
        return line.split(" ");
    }

    private String[] splitIntoHTTPLines(String request) {
        return request.split("\n\n");
    }
}
