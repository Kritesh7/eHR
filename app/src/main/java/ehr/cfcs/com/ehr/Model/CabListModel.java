package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 18-09-2017.
 */

public class CabListModel
{
    public String employName;
    public String zoneName;
    public String cityName;
    public String requestDate;
    public String bookingDate;
    public String status;
    public String followUpDate;
    public String BID;

    public CabListModel(String employName, String zoneName, String cityName, String requestDate,
                        String bookingDate, String status, String followUpDate,String BID) {
        this.employName = employName;
        this.zoneName = zoneName;
        this.cityName = cityName;
        this.requestDate = requestDate;
        this.bookingDate = bookingDate;
        this.status = status;
        this.followUpDate = followUpDate;
        this.BID = BID;
    }

    public String getBID() {
        return BID;
    }

    public String getEmployName() {
        return employName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }
}
