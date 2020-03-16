package server.BRi;

import server.user.UserDB;

import java.io.*;
import java.net.*;
import java.lang.reflect.InvocationTargetException;

class ServiceBRi implements Runnable {
    private Socket socket;
    private UserDB database;

    /**
     * ~ CONSTRUCTOR ~
     *
     * @param socket The socket connecting to the client app.
     */
    ServiceBRi(Socket socket) {
        this.socket = socket;
        this.database = UserDB.getDatabase();
    }

    /**
     * ~ RUN FUNCTION ~
     * Asks the service, launch it and close the connexion.
     */
    public void run() {
        System.out.println("Connexion established with amateur, " + socket);
        String login = null;

        /* trying to launch all that crap */
        try {
            /* TOOLS */
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToUser = new PrintWriter(socket.getOutputStream(), true);
            DataOutputStream dataToUser = new DataOutputStream(socket.getOutputStream());

            /* LOGIN */
            login = inFromUser.readLine();
            dataToUser.writeBoolean(database.has(login));
            String password = inFromUser.readLine();
            dataToUser.writeBoolean(database.has(login, password));
            database.users.get(login).setConnected(true);

            /* CHOOSING */
            outToUser.println(ServiceRegistry.printServicesList() + "`return`Enter service's number :");
            int choice = Integer.parseInt(inFromUser.readLine());

            /* PROCESSING */
            ((iService) ServiceRegistry.getServiceFromIndex(choice).getConstructor(Socket.class).newInstance(socket)).run();

        } catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
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

    /*
    ╭───────────────────────────────────────────────────────────────────────────────────╮
    │ As for ServerBRi, i'm also removing the finalize method bc of trust issues.       │
    │                                                                                   │
    │ See https://howtodoinjava.com/java/basics/why-not-to-use-finalize-method-in-java/ │
    ╰───────────────────────────────────────────────────────────────────────────────────╯

    protected void finalize() throws Throwable {
        socket.close();
    }
    */

    /**
     * Java and its methods not at all boilerplate...
     */
    public void start() {
        (new Thread(this)).start();
    }

}
