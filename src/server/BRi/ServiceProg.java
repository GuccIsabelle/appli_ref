package server.BRi;

import server.user.UserDB;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

public class ServiceProg implements Runnable {
    private final UserDB database;
    private Socket socket;

    /**
     * ~ CONSTRUCTOR ~
     *
     * @param socket The socket connecting to the client app.
     */
    ServiceProg(Socket socket) {
        this.socket = socket;
        this.database = UserDB.getDatabase();
    }

    @Override
    public void run() {
        System.out.println("Connexion established with programmer, " + socket);
        String login = null;

        /* trying to launch all that crap */
        try {
            /* TOOLS */
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToUser = new PrintWriter(socket.getOutputStream(), true);
            DataOutputStream dataToUser = new DataOutputStream(socket.getOutputStream());

            /* LOGIN */
            login = inFromUser.readLine();
            dataToUser.writeBoolean(database.has(login) && database.users.get(login).isProgrammer());
            String password = inFromUser.readLine();
            dataToUser.writeBoolean(database.has(login, password));
            database.users.get(login).setConnected(true);
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL(database.users.get(login).getFTP())});

            /* CHOOSING AND PROCESSING */
            outToUser.println("What do you want to do ?" +
                    "`return``add`    -> a new Service" +
                    "`return``update` -> an existing Service" +
                    "`return``remove` -> an existing Service" +
                    "`return``ftp`    -> to update your FTP server's address");
            switch (inFromUser.readLine()) {
                case "add":
                    outToUser.println("Please, provide Service's name to add:");
                    if (ServiceRegistry.addService(urlClassLoader.loadClass(inFromUser.readLine())))
                        outToUser.println("Service successfully added.");
                    break;
                case "update":
                    outToUser.println("Please, provide Service's name to update:");
                    if (ServiceRegistry.updateService(urlClassLoader.loadClass(inFromUser.readLine())))
                        outToUser.println("Service successfully modified.");
                    break;
                case "remove":
                    outToUser.println("Please, provide Service's name to remove:");
                    if (ServiceRegistry.removeService(urlClassLoader.loadClass(inFromUser.readLine())))
                        outToUser.println("Service successfully removed.");
                    break;
                case "ftp":
                    outToUser.println("Please, provide your new FTP address:");
                    database.users.get(login).setFTP(inFromUser.readLine());
                    outToUser.println("FTP address successfully modified.");
                    break;
            }

        } catch (IOException | ClassNotFoundException | ServiceRegistry.addServiceException e) {
            System.out.println(e.getMessage());
        } finally {
            /* and then trying to close it, talk about efficiency... */
            database.users.get(login).setConnected(false);
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("error while closing, exception be like:\n" + e);
            }
        }
    }

    /**
     * What a nice boilerplate code you got there bud.
     */
    public void start() {
        (new Thread(this)).start();
    }
}
