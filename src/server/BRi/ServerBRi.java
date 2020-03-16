package server.BRi;

import java.io.*;
import java.net.*;

public class ServerBRi implements Runnable {
    private ServerSocket listen_socket;

    /**
     * ~ CONSTRUCTOR ~
     *
     * @param port Where the server needs to listen.
     */
    public ServerBRi(int port) {
        try {
            listen_socket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ~ RUN FUNCTION ~
     *
     * Server listens and accepts connections.
     * For each connection, a ServiceBRi is created and launched.
     */
    public void run() {
        try {
            //noinspection InfiniteLoopStatement
            while (true)
                new ServiceBRi(listen_socket.accept()).start();
        } catch (IOException e) {
            try {
                this.listen_socket.close();
            } catch (IOException ignored) {
            }
            System.err.println("error with port, exception be like:\n" + e);
        }
    }

    /**
     * Return resources -> finalize
     *
     */
    protected void finalize() {
        try {
            this.listen_socket.close();
        } catch (IOException ignored) {
        }
    }
}
