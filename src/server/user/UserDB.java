package server.user;

import java.util.HashMap;

public class UserDB {
    private static UserDB database = null;
    public HashMap<String, User> users = new HashMap<>();

    /**
     * Constructor of the Singleton used for the Database.
     * We put 3 Users by default bc your said we don't have to do registering.
     * (hope I recall correctly...)
     */
    private UserDB() {
        this.users.put("marius", new User("marius", "marius", "Marius", "Vallas", "vallasmarius@gmail.com", "ftp://localhost:2121/", true));
        this.users.put("nils", new User("nils", "nils", "Nils", "Carron", "carronnils@gmail.com", "ftp://localhost:2121/", true));
        this.users.put("brette", new User("brette", "brette", "Jean-François", "Brette", "jean-francois.brette@parisdescartes.fr", null, false));
    }

    /**
     * We synchronize this baby up for obvious reasons.
     *
     * @return The Singleton.
     */
    public synchronized static UserDB getDatabase() {
        if (database == null) database = new UserDB();
        return database;
    }

    /**
     * Check if there is a User for this login.
     *
     * @param readLine the login.
     * @return A pretty obvious boolean.
     */
    public boolean has(String readLine) {
        return this.users.containsKey(readLine) && !this.users.get(readLine).isConnected();
    }

    /**
     * This time we check if the password of the given login matches.
     *
     * @param login    The login, duh.
     * @param readLine The password.
     * @return Another obvious boolean.
     */
    public boolean has(String login, String readLine) {
        return this.users.get(login).getPassword().equals(readLine);
    }
}
