package client.programmer;

import java.io.*;
import java.net.Socket;

/**
 * Connect with the server and communicate through this protocol :
 * ! this protocol involves not using loops !
 * 1. Send ID and wait for reply, quit if negative
 * 2. Send password and wait for reply, quit if negative
 * 3. Get Options list
 * 4. Send chosen Option's ID
 * 5. Get Option's instructions
 * 6. Send User's input
 * 7. Wait for Option's reply
 * 8. Print reply and get the shit out.
 */
public class Appli {
    private final static String HOST = "localhost";
    private final static int PORT = 4000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) {
            /* TOOLS */
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataInputStream dataFromServer = new DataInputStream(socket.getInputStream());
            PrintWriter outToServer = new PrintWriter(socket.getOutputStream(), true);

            /* LOGIN */
            System.out.println("Please, enter your credentials:");
            String login = inFromUser.readLine();
            outToServer.println(login);
            if (!dataFromServer.readBoolean())
                exit("This User is either:" +
                        "\n* already connected" +
                        "\n* not in our database" +
                        "\n* not a programmer");
            String password = inFromUser.readLine();
            outToServer.println(password);
            if (!dataFromServer.readBoolean()) exit("Incorrect password.");

            /* CHOOSING AND PROCESSING */
            System.out.println(inFromServer.readLine().replaceAll("`return`", "\n")); // print list
            outToServer.println(inFromUser.readLine());  // send choice
            System.out.println(inFromServer.readLine()); // print instructions
            outToServer.println(inFromUser.readLine());  // send whatever this is
            System.out.println(inFromServer.readLine());

            // TODO: add loop and quit option

        } catch (IOException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    // TODO: replace this with exception
    private static void exit(String reason) throws IOException {
        System.out.println(reason);
        System.exit(0);
    }
}