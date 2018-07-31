package http;

import java.io.*;

public class IOHelper {

    private InputStream inputStream;
    private OutputStream outputStream;
    private PrintStream printStream;

    public IOHelper(String input) {
        inputStream = new ByteArrayInputStream(input.getBytes());
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
    }

    public InputStream getIn() {
        return inputStream;
    }

    public OutputStream getOut() {
        return outputStream;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public String getStringOutput() {
        return outputStream.toString();
    }
}
