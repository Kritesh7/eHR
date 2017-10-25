package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 18-09-2017.
 */

public class HotelBookingListModel
{
    public String empName;
    public String cityName;
    public String requestDate;
    public String checkInDate;
    public String checkInTime;
    public String checkOutDate;
    public String staus;
    public String followUpDate;

    public HotelBookingListModel(String empName, String cityName, String requestDate, String checkInDate,
                                 String checkInTime, String checkOutDate, String staus, String followUpDate) {
        this.empName = empName;
        this.cityName = cityName;
        this.requestDate = requestDate;
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkOutDate = checkOutDate;
        this.staus = staus;
        this.followUpDate = followUpDate;
    }

    public String getEmplName() {
        return empName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public String getStaus() {
        return staus;
    }

    public String getFollowUpDate() {
        return followUpDate;
    }
}
