package server.user;

public class User {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String FTP;
    private boolean programmer;
    private boolean connected;

    /* auto generated */
    public User(String login, String password, String firstName, String lastName, String address, String FTP, boolean programmer) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.FTP = FTP;
        this.programmer = programmer;
        this.connected = false;
    }

    /* auto generated */
    public String getPassword() {
        return password;
    }

    /* auto generated */
    public boolean isConnected() {
        return connected;
    }

    /* auto generated */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isProgrammer() {
        return programmer;
    }

    public void setProgrammer(boolean programmer) {
        this.programmer = programmer;
    }

    public String getFTP() {
        return FTP;
    }

    public void setFTP(String FTP) {
        this.FTP = FTP;
    }
}
