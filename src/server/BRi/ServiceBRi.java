package server.BRi;

import java.io.*;
import java.net.*;
import java.lang.reflect.InvocationTargetException;

class ServiceBRi implements Runnable {
    private Socket client;

    /**
     * ~ CONSTRUCTOR ~
     *
     * @param socket The socket connecting to the client app.
     */
    ServiceBRi(Socket socket) {
        client = socket;
    }

    /**
     * ~ RUN FUNCTION ~
     *
     * Asks the service, launch it and close the connexion.
     */
    public void run() {
        /* trying to launch all that crap */
        try {
            /* resources */
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter outToClient = new PrintWriter(client.getOutputStream(), true);

            /* asking the user what it want */
            outToClient.println(ServiceRegistry.printServicesList() + "`return`Enter service's number :");

            /* getting the answer */
            int choice = Integer.parseInt(inFromClient.readLine());

            /* launching the chosen service */
            ((iService) ServiceRegistry.getServiceFromIndex(choice).getConstructor(Socket.class).newInstance(client)).run();

        } catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }

        /* and then trying to close it, talk about efficiency... */
        try {
            client.close();
        } catch (IOException e) {
            System.out.println("error while closing, exception be like:\n" + e);
        }
    }

    // todo : test if shit even do something :thinking:
    protected void finalize() throws Throwable {
        client.close();
    }


    // todo : same as the above
    public void start() {
        (new Thread(this)).start();
    }

}
