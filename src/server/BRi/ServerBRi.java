package server.BRi;

import java.io.*;
import java.net.*;

public class ServerBRi implements Runnable {
    private ServerSocket serverSocket;
    private boolean isProg;

    /**
     * ~ CONSTRUCTOR ~
     *
     * @param port Where the server needs to listen.
     */
    public ServerBRi(int port, boolean isProg) {
        this.isProg = isProg;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ~ RUN FUNCTION ~
     * Server listens and accepts connections.
     * For each connection, a ServiceBRi or ServiceProg is created and launched.
     */
    public void run() {
        try {
            if (this.isProg)
                //noinspection InfiniteLoopStatement
                while (true)
                    new ServiceProg(serverSocket.accept()).start();
            else
                //noinspection InfiniteLoopStatement
                while (true)
                    new ServiceBRi(serverSocket.accept()).start();
        } catch (IOException e) {
            try {
                this.serverSocket.close(); // wow, so much efficiency it's almost frightening!
            } catch (IOException ignored) {
                System.out.println("If you see this then you must have done some real-ass dark shit!");
            }
            System.err.println("error with port, exception be like:\n" + e);
        }
    }

    /*
    ╭───────────────────────────────────────────────────────────────────────────────────╮
    │ I'm removing dis shit bc finalize method are not trustworthy.                     │
    │ Furthermore, they even are considerate deprecated in Java 9 and upper.            │
    │                                                                                   │
    │ See https://howtodoinjava.com/java/basics/why-not-to-use-finalize-method-in-java/ │
    ╰───────────────────────────────────────────────────────────────────────────────────╯

    protected void finalize() {
        try {
            this.serverSocket.close();
        } catch (IOException ignored) {
        }
    }
    */
}
