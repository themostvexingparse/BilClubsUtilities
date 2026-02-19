public class Credentials {
    private String password;
    private String username;
    public Credentials(String filename) {
        // implement later with .env
    }
    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
