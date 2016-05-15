package POJO;

/**
 * Created by Adwait on 5/5/2016.
 */

public class User {

    private String id;
    private long userId;
    private String userEmail;
    private String regToken;

    public User() {
    }

    public User(long userId, String userEmail, String regToken) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.regToken = regToken;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public void setUserId(long userId) { this.userId = userId; }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getRegToken() {
        return regToken;
    }
    public void setRegToken(String regToken) {
        this.regToken = regToken;
    }
}
