package services;

import java.io.*;
import java.net.*;

import server.BRi.iService;

public class Inversion implements iService {
    private final Socket client;

    public Inversion(Socket socket) {
        client = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter outToUser = new PrintWriter(client.getOutputStream(), true);

            outToUser.println("Please, input text to reverse");

            outToUser.println(new String(new StringBuffer(inFromUser.readLine()).reverse()));

            client.close();
        } catch (IOException ignored) {
        }
    }

    // todo: check dis shit
    protected void finalize() throws Throwable {
        client.close();
    }

    public static String printDescription() {
        return "Reverses the given text";
    }
}
