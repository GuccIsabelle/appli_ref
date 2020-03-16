package server.BRi;

import java.net.Socket;

public class ServiceProg implements Runnable {
    private Socket socket;

    /**
     * ~ CONSTRUCTOR ~
     *
     * @param socket The socket connecting to the client app.
     */
    ServiceProg(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }
    
    public void start() {
        (new Thread(this)).start();
    }
}
