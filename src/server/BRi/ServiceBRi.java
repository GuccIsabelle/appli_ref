package server.BRi;

import java.io.*;
import java.net.*;
import java.lang.reflect.InvocationTargetException;

class ServiceBRi implements Runnable {
    private Socket socket;

    /**
     * ~ CONSTRUCTOR ~
     *
     * @param socket The socket connecting to the client app.
     */
    ServiceBRi(Socket socket) {
        this.socket = socket;
    }

    /**
     * ~ RUN FUNCTION ~
     * Asks the service, launch it and close the connexion.
     */
    public void run() {
        /* trying to launch all that crap */
        try {
            /* resources */
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(socket.getOutputStream(), true);

            /* asking the user what they want */
            outToClient.println(ServiceRegistry.printServicesList() + "`return`Enter service's number :");

            /* getting the answer */
            int choice = Integer.parseInt(inFromClient.readLine());

            /* launching the chosen service */
            ((iService) ServiceRegistry.getServiceFromIndex(choice).getConstructor(Socket.class).newInstance(socket)).run();

        } catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }

        /* and then trying to close it, talk about efficiency... */
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("error while closing, exception be like:\n" + e);
        }
    }

    /*
    As for ServerBRi, i'm also removing the finalize method bc of trust issues.

    See https://howtodoinjava.com/java/basics/why-not-to-use-finalize-method-in-java/

    protected void finalize() throws Throwable {
        socket.close();
    }
    */

    public void start() {
        (new Thread(this)).start();
    }

}
