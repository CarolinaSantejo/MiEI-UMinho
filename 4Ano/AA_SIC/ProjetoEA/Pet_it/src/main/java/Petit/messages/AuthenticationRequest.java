package Petit.messages;

public class AuthenticationRequest {
    private String Email;
    private String password;


    public AuthenticationRequest(String email, String password) {
        Email = email;
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "Email='" + Email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
