package Petit.classes;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;

import javax.persistence.*;



@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    private String username;
    private String password;
    @Id
    private String email;
    private int phone;
    private String location;
    @Lob
    private byte[] image;

    public User() {
    }


    public User(String username, String password, String email, int phone, String location) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.location = location;
    }



    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return this.phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "{" +
            "username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", location='" + getLocation() + "'";
    }

}
