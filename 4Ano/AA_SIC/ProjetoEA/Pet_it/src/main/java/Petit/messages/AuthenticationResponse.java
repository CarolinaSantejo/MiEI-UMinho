package Petit.messages;

public class AuthenticationResponse {

    private boolean res;
    private String userType;
    private String username;

    public AuthenticationResponse(boolean res, String userType, String username){
        this.res = res;
        this.userType = userType;
        this.username = username;
    }

    public AuthenticationResponse() {
    }

    public boolean getRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
