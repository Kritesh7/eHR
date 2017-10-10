package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 10-10-2017.
 */

public class AttendanceListModel
{
    public String AttendanceLogID;
    public String Date;
    public String InTime;
    public String OutTime;
    public String WorkTime;
    public String Halfday;
    public String LateArrival;
    public String EarlyLeaving;
    public String Status;


    public AttendanceListModel(String attendanceLogID, String date, String inTime,
                               String outTime, String workTime, String halfday, String lateArrival, String earlyLeaving, String status) {
        AttendanceLogID = attendanceLogID;
        Date = date;
        InTime = inTime;
        OutTime = outTime;
        WorkTime = workTime;
        Halfday = halfday;
        LateArrival = lateArrival;
        EarlyLeaving = earlyLeaving;
        Status = status;
    }

    public String getAttendanceLogID() {
        return AttendanceLogID;
    }

    public String getDate() {
        return Date;
    }

    public String getInTime() {
        return InTime;
    }

    public String getOutTime() {
        return OutTime;
    }

    public String getWorkTime() {
        return WorkTime;
    }

    public String getHalfday() {
        return Halfday;
    }

    public String getLateArrival() {
        return LateArrival;
    }

    public String getEarlyLeaving() {
        return EarlyLeaving;
    }

    public String getStatus() {
        return Status;
    }
}
