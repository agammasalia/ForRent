package POJO;


/**
 * Created by Adwait on 5/5/2016.
 */
public class Location {

    private String id;
    private long locationId;
    private String locationStreetAddress;
    private String locationCity;
    private String locationState;
    private String locationZip;

    public Location()
    {}

    public Location(long locationId, String locationStreetAddress, String locationCity, String locationState, String locationZip) {
        this.locationId = locationId;
        this.locationStreetAddress = locationStreetAddress;
        this.locationCity = locationCity;
        this.locationState = locationState;
        this.locationZip = locationZip;
    }

    public Location(String locationStreetAddress, String locationCity, String locationState, String locationZip) {
        this.locationStreetAddress = locationStreetAddress;
        this.locationCity = locationCity;
        this.locationState = locationState;
        this.locationZip = locationZip;
    }

    public String getId() {
        return id;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getLocationStreetAddress() {
        return locationStreetAddress;
    }

    public void setLocationStreetAddress(String locationStreetAddress) {
        this.locationStreetAddress = locationStreetAddress;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationState() {
        return locationState;
    }

    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }

    public String getLocationZip() {
        return locationZip;
    }

    public void setLocationZip(String locationZip) {
        this.locationZip = locationZip;
    }
}
