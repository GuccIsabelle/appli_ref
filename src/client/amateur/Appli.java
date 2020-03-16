package client.amateur;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Connect with the server and communicate through this protocol :
 * ! this protocol involves not using loops !
 * 1. Send ID and wait for reply, quit if negative
 * 2. Send password and wait for reply, quit if negative
 * 3. Get Services list
 * 4. Send chosen Service's ID
 * 5. Get Service's instructions
 * 6. Send User's input
 * 7. Wait for Service's reply
 * 8. Print reply and get the shit out.
 */
public class Appli {
    private final static String HOST = "localhost";
    private final static int PORT = 3000;

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
            String password = inFromUser.readLine();
            outToServer.println(login);
            if (!dataFromServer.readBoolean()) exit("This User is either:\n* already connected\n* not in our database");
            outToServer.println(password);
            if (!dataFromServer.readBoolean()) exit("Incorrect password.");

            /* CHOOSING */
            System.out.println(inFromServer.readLine().replaceAll("`return`", "\n")); // printing all Services
            outToServer.println(inFromUser.readLine()); // sending chosen Service's index
            if (!dataFromServer.readBoolean()) exit("Something went wrong.\n Their's two options:\neither the user is dumb\nor the server is dumb\n\nOption one is more likely...");

            /* PROCESSING */
            System.out.println(inFromServer.readLine().replaceAll("`return`", "\n")); // printing instructions
            outToServer.println(inFromUser.readLine()); // sending User's reply
            System.out.println(inFromServer.readLine().replaceAll("`return`", "\n")); // printing results

            // TODO: add loop and quit option

        } catch (IOException e) {
            System.out.println("Something went wrong, this something be like:");
            e.printStackTrace();
        }
    }

    private static void exit(String reason) throws IOException {
        System.out.println(reason);
        System.exit(0);
    }
}
