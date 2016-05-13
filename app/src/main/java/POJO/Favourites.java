package POJO;

/**
 * Created by Adwait on 5/5/2016.
 */
public class Favourites {
    private String id;

    private long favouritesId;
    private long favouritesUserId;
    private long favouritesPropertyId;

    public Favourites(){}

    public Favourites(long favouritesId, long favouritesUserId, long favouritesPropertyId) {
        this.favouritesId = favouritesId;
        this.favouritesUserId = favouritesUserId;
        this.favouritesPropertyId = favouritesPropertyId;
    }

    public Favourites(long favouritesUserId, long favouritesPropertyId) {
        this.favouritesUserId = favouritesUserId;
        this.favouritesPropertyId = favouritesPropertyId;
    }




    public String getId() {
        return id;
    }

    public long getFavouritesId() {
        return favouritesId;
    }

    public void setFavouritesId(long favouritesId) {
        this.favouritesId = favouritesId;
    }

    public long getFavouritesUserId() {
        return favouritesUserId;
    }

    public void setFavouritesUserId(long favouritesUserId) {
        this.favouritesUserId = favouritesUserId;
    }

    public long getFavouritesPropertyId() {
        return favouritesPropertyId;
    }

    public void setFavouritesPropertyId(long favouritesPropertyId) {
        this.favouritesPropertyId = favouritesPropertyId;
    }
}