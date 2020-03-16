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
            String password = inFromUser.readLine();
            outToServer.println(login);
            if (!dataFromServer.readBoolean()) exit("This User is either:\n* already connected\n* not in our database");
            outToServer.println(password);
            if (!dataFromServer.readBoolean()) exit("Incorrect password.");

            /* CHOOSING AND PROCESSING */
            switch (inFromUser.readLine()) {
                case "add":
                    System.out.println("Please, provide Service's name:");
                    if (!addService(inFromUser.readLine())) exit("Service doesn't exist in your FTP server.");
                    break;
                case "update":
                    if (!updateService(inFromUser.readLine()))
                        exit("Service doesn't exist, please check the name again.");
                    break;
                case "ftp":
                    if (!modifyFTP(inFromUser.readLine())) exit("Error append while modifying your FTP server.");
                    break;
                default:
                    System.out.println("Sorry I didn't get that.");
            }

            // TODO: add loop and quit option

        } catch (IOException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        }
    }

    private static boolean addService(String serviceName) {
    }

    private static boolean updateService(String serviceName) {

    }

    private static boolean modifyFTP(String FTP) {

    }

    private static void exit(String reason) throws IOException {
        System.out.println(reason);
        System.exit(0);
    }
}