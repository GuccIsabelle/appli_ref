package server.application;

import server.BRi.ServerBRi;

import java.net.MalformedURLException;

public class Server {
    private final static int PORT_AMATEUR = 3000;
    private final static int PORT_PROGRAMMER = 4000;

    /**
     * Main method of the Server. Nothing much here.
     *
     * @param args You know what this is.
     */
    public static void main(String[] args) {

        new Thread(new ServerBRi(PORT_AMATEUR, false)).start();
        new Thread(new ServerBRi(PORT_PROGRAMMER, true)).start();

        System.out.println("Servers started");
    }
}
