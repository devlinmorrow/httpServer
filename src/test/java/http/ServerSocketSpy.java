package http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketSpy extends ServerSocket {

    private Socket socket;
    private int numberAcceptCalled;

    public ServerSocketSpy(Socket socket) throws IOException {
        this.socket = socket;
        numberAcceptCalled = 0;
    }

    @Override
    public Socket accept() {
        numberAcceptCalled++;
        return socket;
    }

    public int howManyTimesAcceptCalled() {
        return numberAcceptCalled;
    }
}
