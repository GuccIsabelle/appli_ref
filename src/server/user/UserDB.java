package server.user;

import java.util.HashMap;

public class UserDB {
    private static UserDB database = null;
    public HashMap<String, User> users = new HashMap<>();

    private UserDB() {
        this.users.put("login", new Programmer("login", "password", "Marius", "Vallas", "vallasmarius@gmail.com", "ftp://localhost:2121/"));
    }

    public synchronized static UserDB getDatabase() {
        if (database == null) database = new UserDB();
        return database;
    }
}
