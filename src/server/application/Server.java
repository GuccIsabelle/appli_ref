package server.application;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Scanner;

import server.BRi.ServerBRi;
import server.BRi.ServiceRegistry;
import server.user.User;
import server.user.Programmer;

public class Server {
    private static HashMap<String, User> clients = new HashMap<>();
    private final static int PORT_AMATEUR = 3000;
    private final static int PORT_PROGRAMMER = 4000;

    public static void main(String[] args) throws MalformedURLException {
        // create test programmer
        Programmer testProgrammer = createTestProgrammer();
        clients.put(testProgrammer.getLogin(), testProgrammer);

        Scanner inFromConsole = new Scanner(System.in);

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("ftp://localhost:2121/")});

        new Thread(new ServerBRi(PORT_AMATEUR)).start();
        //new Thread(new ServerBRi(PORT_PROGRAMMER)).start();

        System.out.println("server started");

        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                ServiceRegistry.addService(urlClassLoader.loadClass(inFromConsole.next()));
                System.out.println("Class was loaded correctly");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Programmer createTestProgrammer() {
        return new Programmer("login", "password", "Vallas", "Marius", "vallasmarius@gmail.com", "ftp://localhost:2121/");
    }
}
