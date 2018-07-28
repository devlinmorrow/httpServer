package http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketStubSpy extends Socket {

    private InputStream in;
    private OutputStream out;
    private boolean closeWasCalled;

    public SocketStubSpy(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
        closeWasCalled = false;
    }

    @Override
    public OutputStream getOutputStream() {
        return out;
    }

    @Override
    public InputStream getInputStream() {
        return in;
    }

    @Override
    public void close() {
        closeWasCalled = true;
        try {
            super.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean wasClosedCalled() {
        return closeWasCalled;
    }

}
