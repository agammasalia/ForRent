package POJO;


/**
 * Created by Adwait on 5/5/2016.
 */
public class Property {


    private String id;
    private long propertyId;
    private long propertyOwnerId;
    private long propertyLocationId;
    private String propertyType;
    private double propertyPrice;
    private String propertyDescription;
    private String propertyTitle;
    private String propertyOwnerEmail;
    private String propertyOwnerPhone;
    private String propertyStreetAddress;
    private String propertyCity;
    private String propertyState;
    private String propertyZip;
    private int propertyNumberOfBaths;
    private int propertyNumberOfRooms;
    private double propertySquareFootage;
    private String propertyRentStatus;

    public Property()
    {}

    public Property(long propertyOwnerId, String propertyType, double propertyPrice, String propertyDescription, String propertyTitle, String propertyOwnerEmail, String propertyOwnerPhone, String propertyStreetAddress, String propertyCity, String propertyState, String propertyZip, int propertyNumberOfBaths, int propertyNumberOfRooms, double propertySquareFootage, String propertyRentStatus) {
        this.propertyOwnerId = propertyOwnerId;
        this.propertyType = propertyType;
        this.propertyPrice = propertyPrice;
        this.propertyDescription = propertyDescription;
        this.propertyTitle = propertyTitle;
        this.propertyOwnerEmail = propertyOwnerEmail;
        this.propertyOwnerPhone = propertyOwnerPhone;
        this.propertyStreetAddress = propertyStreetAddress;
        this.propertyCity = propertyCity;
        this.propertyState = propertyState;
        this.propertyZip = propertyZip;
        this.propertyNumberOfBaths = propertyNumberOfBaths;
        this.propertyNumberOfRooms = propertyNumberOfRooms;
        this.propertySquareFootage = propertySquareFootage;
        this.propertyRentStatus = propertyRentStatus;
    }

    public String getId() {
        return id;
    }

    public int getPropertyNumberOfRooms() {
        return propertyNumberOfRooms;
    }

    public void setPropertyNumberOfRooms(int propertyNumberOfRooms) {
        this.propertyNumberOfRooms = propertyNumberOfRooms;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public long getPropertyOwnerId() {
        return propertyOwnerId;
    }

    public long getPropertyLocationId() {
        return propertyLocationId;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public double getPropertyPrice() {
        return propertyPrice;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public String getPropertyOwnerEmail() {
        return propertyOwnerEmail;
    }

    public String getPropertyOwnerPhone() {
        return propertyOwnerPhone;
    }

    public String getPropertyStreetAddress() {
        return propertyStreetAddress;
    }

    public String getPropertyCity() {
        return propertyCity;
    }

    public String getPropertyState() {
        return propertyState;
    }

    public String getPropertyZip() {
        return propertyZip;
    }

    public int getPropertyNumberOfBaths() {
        return propertyNumberOfBaths;
    }

    public double getPropertySquareFootage() {
        return propertySquareFootage;
    }

    public String getPropertyRentStatus() {
        return propertyRentStatus;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    public void setPropertyOwnerId(long propertyOwnerId) {
        this.propertyOwnerId = propertyOwnerId;
    }

    public void setPropertyLocationId(long propertyLocationId) {
        this.propertyLocationId = propertyLocationId;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public void setPropertyPrice(double propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public void setPropertyOwnerEmail(String propertyOwnerEmail) {
        this.propertyOwnerEmail = propertyOwnerEmail;
    }

    public void setPropertyOwnerPhone(String propertyOwnerPhone) {
        this.propertyOwnerPhone = propertyOwnerPhone;
    }

    public void setPropertyStreetAddress(String propertyStreetAddress) {
        this.propertyStreetAddress = propertyStreetAddress;
    }

    public void setPropertyCity(String propertyCity) {
        this.propertyCity = propertyCity;
    }

    public void setPropertyState(String propertyState) {
        this.propertyState = propertyState;
    }

    public void setPropertyZip(String propertyZip) {
        this.propertyZip = propertyZip;
    }

    public void setPropertyNumberOfBaths(int propertyNumberOfBaths) {
        this.propertyNumberOfBaths = propertyNumberOfBaths;
    }

    public void setPropertySquareFootage(double propertySquareFootage) {
        this.propertySquareFootage = propertySquareFootage;
    }

    public void setPropertyRentStatus(String propertyRentStatus) {
        this.propertyRentStatus = propertyRentStatus;
    }

    @Override
    public String toString() {
        return "Property{" +
                "propertyId=" + propertyId +
                ", propertyOwnerId=" + propertyOwnerId +
                ", propertyLocationId=" + propertyLocationId +
                ", propertyType='" + propertyType + '\'' +
                ", propertyPrice=" + propertyPrice +
                ", propertyDescription='" + propertyDescription + '\'' +
                ", propertyTitle='" + propertyTitle + '\'' +
                ", propertyOwnerEmail='" + propertyOwnerEmail + '\'' +
                ", propertyOwnerPhone='" + propertyOwnerPhone + '\'' +
                ", propertyStreetAddress='" + propertyStreetAddress + '\'' +
                ", propertyCity='" + propertyCity + '\'' +
                ", propertyState='" + propertyState + '\'' +
                ", propertyZip='" + propertyZip + '\'' +
                ", propertyNumberOfBaths=" + propertyNumberOfBaths +
                ", propertyNumberOfRooms=" + propertyNumberOfRooms +
                ", propertySquareFootage=" + propertySquareFootage +
                ", propertyRentStatus='" + propertyRentStatus + '\'' +
                '}';
    }

}
