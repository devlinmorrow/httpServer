package http.Requesters;

import java.io.InputStream;
import java.util.Scanner;

public class RequestReader {

    private Scanner scanner;

    public RequestReader(InputStream clientIn) {
        scanner = new Scanner(clientIn);
    }

    public String extractRequestLine() {
        return scanner.nextLine();
    }

    public String extractHeaders() {
        StringBuilder headers = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            } else {
                headers.append(line).append("\n");
            }
        }
        return new String(headers);
    }

    public String extractBodyContent() {
        StringBuilder bodyContent = new StringBuilder();
        while (scanner.hasNextLine()) {
            bodyContent.append(scanner.nextLine()).append("\n");
        }
        return new String(bodyContent);
    }

}
