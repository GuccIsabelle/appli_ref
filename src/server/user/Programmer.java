package server.user;

public class Programmer extends User {
    private String FTP;

    /* auto generated */
    public Programmer(String login, String password, String firstName, String lastName, String address, String FTP) {
        super(login, password, firstName, lastName, address);
        this.FTP = FTP;
    }

    /* auto generated */
    public String getFTP() {
        return FTP;
    }

    /* auto generated */
    public void setFTP(String FTP) {
        this.FTP = FTP;
    }
}
