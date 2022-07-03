package Petit.messages;

public class GetMakeReqRequest {
    private String emailPetsitter;
    private String email;

    public GetMakeReqRequest() {
    }

    public String getEmailPetsitter() {
        return emailPetsitter;
    }

    public void setEmailPetsitter(String emailPetsitter) {
        this.emailPetsitter = emailPetsitter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
