package POJO;


/**
 * Created by Adwait on 5/5/2016.
 */
public class SavedSearch {
    private String id;
    private long searchId;
    private long searchUserId;
    private double searchHighPrice;
    private double searchLowPrice;
    private String searchKeyword;
    private String searchCity;
    private String searchZip;
    private String searchPropertyType;

    public SavedSearch(){}

    public SavedSearch(long searchId, long searchUserId, double searchHighPrice, double searchLowPrice, String searchKeyword, String searchCity, String searchZip, String searchPropertyType) {
        this.searchId = searchId;
        this.searchUserId = searchUserId;
        this.searchHighPrice = searchHighPrice;
        this.searchLowPrice = searchLowPrice;
        this.searchKeyword = searchKeyword;
        this.searchCity = searchCity;
        this.searchZip = searchZip;
        this.searchPropertyType = searchPropertyType;
    }

    public String getSearchCity() {
        return searchCity;
    }

    public void setSearchCity(String searchCity) {
        this.searchCity = searchCity;
    }

    public String getSearchZip() {
        return searchZip;
    }

    public void setSearchZip(String searchZip) {
        this.searchZip = searchZip;
    }

    public long getSearchId() {
        return searchId;
    }

    public void setSearchId(long searchId) {
        this.searchId = searchId;
    }

    public long getSearchUserId() {
        return searchUserId;
    }

    public void setSearchUserId(long searchUserId) {
        this.searchUserId = searchUserId;
    }

    public double getSearchHighPrice() {
        return searchHighPrice;
    }

    public void setSearchHighPrice(double searchHighPrice) {
        this.searchHighPrice = searchHighPrice;
    }

    public double getSearchLowPrice() {
        return searchLowPrice;
    }

    public void setSearchLowPrice(double searchLowPrice) {
        this.searchLowPrice = searchLowPrice;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getSearchPropertyType() {
        return searchPropertyType;
    }

    public void setSearchPropertyType(String searchPropertyType) {
        this.searchPropertyType = searchPropertyType;
    }
}
