package Petit.messages;

public class RegisterClientRequest {
    String email;
    String password;
    String username;
    int phone;
    String location;


    public RegisterClientRequest(String email, String password, String username, int phone, String location) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
