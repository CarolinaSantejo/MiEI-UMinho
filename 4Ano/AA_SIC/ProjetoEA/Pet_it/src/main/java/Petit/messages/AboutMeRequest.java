package Petit.messages;

public class AboutMeRequest {
    String email;
    String about_text;

    public AboutMeRequest(String email, String about_text) {
        this.email = email;
        this.about_text = about_text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAbout_text() {
        return about_text;
    }

    public void setAbout_text(String about_text) {
        this.about_text = about_text;
    }
}
