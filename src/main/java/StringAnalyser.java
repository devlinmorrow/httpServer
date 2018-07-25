public class StringAnalyser {

    public String findHTTPVerb(String request) {
        String[] linesOfRequest = splitIntoLines(request);
        String[] wordsOfLine = splitIntoWords(linesOfRequest[0]);
        return wordsOfLine[0];
    }

    public String findFilePathName(String request) {
        String[] linesOfRequest = splitIntoLines(request);
        String[] wordsOfLine = splitIntoWords(linesOfRequest[0]);
        return wordsOfLine[1];
    }

    private String[] splitIntoWords(String line) {
        return line.split(" ");
    }

    private String[] splitIntoLines(String request) {
        return request.split("\n\n");
    }
}
