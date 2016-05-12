package POJO;


/**
 * Created by Adwait on 5/5/2016.
 */
public class Notification {
    private String id;
    private long notificationId;
    private long notificationSearchId;
    private long notificationPropertyId;

    public Notification(long notificationId, long notificationSearchId, long notificationPropertyId) {
        this.notificationId = notificationId;
        this.notificationSearchId = notificationSearchId;
        this.notificationPropertyId = notificationPropertyId;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public long getNotificationSearchId() {
        return notificationSearchId;
    }

    public void setNotificationSearchId(long notificationSearchId) {
        this.notificationSearchId = notificationSearchId;
    }

    public long getNotificationPropertyId() {
        return notificationPropertyId;
    }

    public void setNotificationPropertyId(long notificationPropertyId) {
        this.notificationPropertyId = notificationPropertyId;
    }
}
