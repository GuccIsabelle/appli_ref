package services;

import server.BRi.iService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Count implements iService {
    private final Socket socket;

    public Count(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            /* TOOLS */
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToUser = new PrintWriter(socket.getOutputStream(), true);

            /* sending instructions to the user */
            outToUser.println(" Please, input text to count");

            /* getting user's input and sending the processed data back */
            outToUser.println(inFromUser.readLine().length());

            /* closing, duh... */
            socket.close();
        } catch (IOException ignored) {
        }
    }

    public static String printDescription() {
        return "Return the given text's size";
    }
}
