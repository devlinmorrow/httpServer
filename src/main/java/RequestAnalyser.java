public class RequestAnalyser {

    public String findHTTPVerb(String request) {
        String[] linesOfRequest = splitIntoLines(request);
        String[] wordsOfLine = splitIntoWords(linesOfRequest[0]);
        return wordsOfLine[0];
    }

    private String[] splitIntoWords(String line) {
        return line.split(" ");
    }

    private String[] splitIntoLines(String request) {
        return request.split("\n\n");
    }
}
