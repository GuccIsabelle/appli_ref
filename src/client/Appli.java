package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Connect with the server and communicate through this protocol :
 * ! this protocol involves not using loops !
 * 1. Get and Print Services menu
 * 2. Send back the chosen Service
 * 3. Get and Print Service's instructions
 * 4. Send back User's input
 * 5. Get and Print Service's result
 * 6. Get the shit out of there...
 */
public class Appli {
    private final static String HOST = "localhost";
    private final static int PORT = 3000;

    public static void main(String[] args) {
        Socket socket;
        try {
            socket = new Socket(HOST, PORT);

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

            /* 0. printing info */
            System.out.println(String.format("Connected to %s:%d", socket.getInetAddress(), socket.getPort()));

            /* 1. getting and printing the list of Services */
            System.out.println(inFromServer.readLine());

            /* 2. sending to server index of the wanted Service */
            outToServer.println(inFromUser.readLine());

            /* 3. printing what the Service want the user to do */
            System.out.println(inFromServer.readLine());

            /* 4. sending back the user's input to the Service */
            outToServer.println(inFromUser.readLine());

            /* 5. printing the final result */
            System.out.println(inFromServer.readLine());

            /* 6. u know how to read I guess */
            socket.close();

        } catch (IOException e) {
            System.err.println("oof, connection ended, exception be like:\n" + e);
        }
    }
}
