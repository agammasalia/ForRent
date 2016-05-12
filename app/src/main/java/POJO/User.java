package POJO;

/**
 * Created by Adwait on 5/5/2016.
 */

public class User {

    private String id;
    private long userId;
    private String userEmail;


    public User() {
    }

    public User(long userId, String userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
