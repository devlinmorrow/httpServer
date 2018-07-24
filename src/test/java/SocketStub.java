import jdk.internal.util.xml.impl.Input;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketStub extends Socket {

    private InputStream in;
    private OutputStream out;

    public SocketStub(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public OutputStream getOutputStream() {
        return out;
    }

    @Override
    public InputStream getInputStream() {
        return in;
    }

    public String getOutput() {
        return out.toString();
    }
}
