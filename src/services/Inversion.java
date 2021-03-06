package services;

import java.io.*;
import java.net.*;

import server.BRi.iService;

public class Inversion implements iService {
    private final Socket socket;

    public Inversion(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            /* TOOLS */
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToUser = new PrintWriter(socket.getOutputStream(), true);

            /* sending instructions to the user */
            outToUser.println(" Please, input text to reverse");

            /* getting user's input and sending the processed data back */
            outToUser.println(new String(new StringBuffer(inFromUser.readLine()).reverse()));

            /* closing, duh... */
            socket.close();
        } catch (IOException ignored) {
        }
    }

    public static String printDescription() {
        return "Reverses the given text";
    }
}
