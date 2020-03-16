package server.user;

public abstract class User {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private boolean connected;

    /* auto generated */
    public User(String login, String password, String firstName, String lastName, String address) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.connected = false;
    }

    /* auto generated */
    public String getLogin() {
        return login;
    }

    /* auto generated */
    public void setLogin(String login) {
        this.login = login;
    }

    /* auto generated */
    public String getPassword() {
        return password;
    }

    /* auto generated */
    public void setPassword(String password) {
        this.password = password;
    }

    /* auto generated */
    public String getFirstName() {
        return firstName;
    }

    /* auto generated */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /* auto generated */
    public String getLastName() {
        return lastName;
    }

    /* auto generated */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /* auto generated */
    public String getAddress() {
        return address;
    }

    /* auto generated */
    public void setAddress(String address) {
        this.address = address;
    }

    /* auto generated */
    public boolean isConnected() {
        return connected;
    }

    /* auto generated */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
